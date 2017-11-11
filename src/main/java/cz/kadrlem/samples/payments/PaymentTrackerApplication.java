package cz.kadrlem.samples.payments;

import cz.kadrlem.samples.payments.domain.Payment;
import cz.kadrlem.samples.payments.service.PaymentStringConverter;
import cz.kadrlem.samples.payments.service.PaymentStringException;
import cz.kadrlem.samples.payments.service.PaymentTrackerException;
import cz.kadrlem.samples.payments.service.PaymentTrackerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

/**
 * Main Spring boot application.
 */
@SpringBootApplication
public class PaymentTrackerApplication implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentTrackerApplication.class);

    private static final String QUIT = "quit";

    @Autowired
    private PaymentTrackerService paymentTrackerService;

    @Autowired
    private PaymentStringConverter paymentStringConverter;

    @Override
    public void run(String... args) {

        if (!processArgs(args)){
            return;
        }

        boolean quit = false;
        LOGGER.info("Accepting payments e.g. EUR 100: ");
        while (!quit){
            Scanner scanner = new Scanner (System.in);

            String input = scanner.nextLine();

            if (QUIT.equals(input)){
                quit = true;
            }else{
                try {
                    Payment payment = paymentStringConverter.toPayment(input);
                    paymentTrackerService.savePayment(payment);
                    LOGGER.info("{} accepted", payment);
                }catch (PaymentStringException e){
                    LOGGER.error("Invalid payment.", e);
                }
            }
        }
    }

    private boolean processArgs(String... args){
        if (args.length > 0){
            try {
                paymentTrackerService.processInputFile(args[0]);
                LOGGER.info("File {} processed.", args[0]);
                return true;
            }catch (PaymentTrackerException e){
                LOGGER.error("Invalid payment file.", e);
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) throws Exception {
        SpringApplication springApplication = new SpringApplication(PaymentTrackerApplication.class);
        springApplication.setLogStartupInfo(false);
        springApplication.run(args).close();
    }

}
