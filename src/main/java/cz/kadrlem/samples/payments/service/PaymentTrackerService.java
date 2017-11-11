package cz.kadrlem.samples.payments.service;

import cz.kadrlem.samples.payments.domain.Payment;
import cz.kadrlem.samples.payments.dto.BalanceDto;

import java.util.List;

/**
 * Service to work with payments.
 */
public interface PaymentTrackerService {

    /**
     * Stores new {@link Payment} entity into DB.
     *
     * @param payment payment to store
     */
    void savePayment(Payment payment);

    /**
     * Returns balances for all known currencies.
     * Includes also zero balances.
     */
    List<BalanceDto> getCurrencyBalances();

    /**
     * Processes input file and stores all included payments.
     * @param inputFile path to input file
     */
    void processInputFile(String inputFile);
}
