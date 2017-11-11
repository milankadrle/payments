package cz.kadrlem.samples.payments.service.impl;

import cz.kadrlem.samples.payments.service.CurrencyConverter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Implementation of CurrencyConverter with hardcoded exchange rates.
 */
@Component
public class CurrencyConverterImpl implements CurrencyConverter{

    private static final BigDecimal EUR_RATE = new BigDecimal("1.12");
    private static final BigDecimal GBP_RATE = new BigDecimal("1.28");
    private static final BigDecimal JPY_RATE = new BigDecimal("0.009");

    @Override
    public Optional<BigDecimal> convertAmountToUsd(String currencyCode, BigDecimal amount) {

        switch (currencyCode) {
            case "USD":
                return Optional.of(amount);
            case "EUR":
                return Optional.of(amount.multiply(EUR_RATE));
            case "GBP":
                return Optional.of(amount.multiply(GBP_RATE));
            case "JPY":
                return Optional.of(amount.multiply(JPY_RATE));
            default:
                return Optional.empty();
        }
    }
}
