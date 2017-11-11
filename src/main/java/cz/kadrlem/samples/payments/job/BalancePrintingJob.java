package cz.kadrlem.samples.payments.job;

import cz.kadrlem.samples.payments.dto.BalanceDto;
import cz.kadrlem.samples.payments.service.PaymentStringConverter;
import cz.kadrlem.samples.payments.service.PaymentTrackerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Job executed once a minute writes current balances for all currencies.
 */
@Component
public class BalancePrintingJob {

    private static final Logger LOGGER = LoggerFactory.getLogger(BalancePrintingJob.class);

    @Autowired
    private PaymentTrackerService paymentTrackerService;

    @Autowired
    private PaymentStringConverter paymentStringConverter;

    @Scheduled(initialDelay = 60000, fixedRate = 60000)
    public void reportCurrentTime() {

        StringBuilder balancesSb = new StringBuilder();
        balancesSb.append("Currency balances:").append(System.lineSeparator());
        for (BalanceDto balance : paymentTrackerService.getCurrencyBalances()){
            if (BigDecimal.ZERO.compareTo(balance.getBalance()) != 0){
                balancesSb.append(paymentStringConverter.fromBalanceDto(balance)).append(System.lineSeparator());
            }
        }
        String balances = balancesSb.toString();
        LOGGER.info(balances);
    }

}
