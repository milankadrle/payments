package cz.kadrlem.samples.payments.service;

import cz.kadrlem.samples.payments.domain.Payment;
import cz.kadrlem.samples.payments.dto.BalanceDto;

/**
 * Handles conversion of input payment string into {@link Payment} entity and from {@link BalanceDto} to payment string.
 */
public interface PaymentStringConverter {

    /**
     * Converts input payment string to new {@link Payment} entity.
     *
     * @param paymentString Payment string is expected to have three uppercase letters for currency followed by
     *                      space and amount without decimal places. E.g. "USD 2000". Note that existence of the currency
     *                      code is not validated.
     * @return newly created Payment entity
     * @throws PaymentStringException in case payment string is malformed
     */
    Payment toPayment(String paymentString);

    /**
     * Converts {@link BalanceDto} to payment string.
     *
     */
    String fromBalanceDto(BalanceDto balanceDto);
}
