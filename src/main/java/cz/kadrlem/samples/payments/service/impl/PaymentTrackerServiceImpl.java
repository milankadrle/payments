package cz.kadrlem.samples.payments.service.impl;

import cz.kadrlem.samples.payments.domain.Payment;
import cz.kadrlem.samples.payments.dto.BalanceDto;
import cz.kadrlem.samples.payments.repository.PaymentRepository;
import cz.kadrlem.samples.payments.service.CurrencyConverter;
import cz.kadrlem.samples.payments.service.PaymentStringConverter;
import cz.kadrlem.samples.payments.service.PaymentTrackerException;
import cz.kadrlem.samples.payments.service.PaymentTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Default implementation of {@link PaymentTrackerService}.
 */
@Component
public class PaymentTrackerServiceImpl implements PaymentTrackerService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CurrencyConverter currencyConverter;

    @Autowired
    private PaymentStringConverter paymentStringConverter;

    @Override
    public void savePayment(Payment payment) {
        paymentRepository.save(payment);
    }

    @Override
    public List<BalanceDto> getCurrencyBalances() {
        //NOTE: it would be possible to use database group by instead of loading whole entities and grouping here

        List<Payment> payments = paymentRepository.findAll();
        Map<String, List<Payment>> grouped = payments.stream().collect(Collectors.groupingBy(Payment::getCurrencyCode));
        Map<String, List<Payment>> sorted = new TreeMap<>(grouped);

        List<BalanceDto> balanceDtos = new ArrayList<>();
        for (Map.Entry<String, List<Payment>> groupedEntry : sorted.entrySet()) {
            String currencyCode = groupedEntry.getKey();
            BigDecimal balance = groupedEntry.getValue().stream().map(Payment::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            Optional<BigDecimal> usdBalance = currencyConverter.convertAmountToUsd(currencyCode, balance);
            balanceDtos.add(new BalanceDto(currencyCode, balance, usdBalance));
        }

        return balanceDtos;
    }

    @Override
    public void processInputFile(String inputFile) {
        try {
            Files.readAllLines(new File(inputFile).toPath()).forEach(s -> savePayment(paymentStringConverter.toPayment(s)));
        } catch (IOException e) {
            throw new PaymentTrackerException(e);
        }
    }

}
