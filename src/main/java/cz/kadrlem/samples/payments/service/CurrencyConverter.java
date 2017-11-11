package cz.kadrlem.samples.payments.service;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * CurrencyConverter is responsible for converting amounts between currencies.
 */
public interface CurrencyConverter {

    /**
     * Converts amount in currency specified by currencyCode to amount in USD.
     * @param currencyCode three character currency code
     * @param amount amount in the specified currency
     *
     * @return Optional of amount in USD. Optionla is empty if exchange rate is not known for the specified currency
     */
    Optional<BigDecimal> convertAmountToUsd(String currencyCode, BigDecimal amount);

}
