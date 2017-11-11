package cz.kadrlem.samples.payments.service.impl;

import cz.kadrlem.samples.payments.constant.PaymentTrackerConstants;
import cz.kadrlem.samples.payments.domain.Payment;
import cz.kadrlem.samples.payments.dto.BalanceDto;
import cz.kadrlem.samples.payments.service.PaymentStringConverter;
import cz.kadrlem.samples.payments.service.PaymentStringException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isNumeric;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;

/**
 * Default implementation of {@link PaymentStringConverter}.
 */
@Component
public class PaymentStringConverterImpl implements PaymentStringConverter{

    private static final int CURRENCY_CODE_LENGTH = 3;

    @Override
    public Payment toPayment(String paymentString) {
        String[] parts = StringUtils.split(trimToEmpty(paymentString), PaymentTrackerConstants.PAYMENT_FORMAT_SEPARATOR);
        if (parts.length == 2){
            String currency = parts[0];
            String amountString = parts[1];

            validateCurrency(currency);
            BigDecimal amount = convertToBigDecimal(amountString);

            return new Payment(currency, amount);
        }else{
            throw new PaymentStringException("Invalid payment format. Payment is expected to have 3 uppercase currency code letters followed by space and integer amount.");
        }
    }

    @Override
    public String fromBalanceDto(BalanceDto balance) {
        return StringUtils.join(new String[] {balance.getCurrencyCode(), balance.getBalance().toPlainString(),
                getUsdEquivalentString(balance.getUsdEquivalent())}, PaymentTrackerConstants.PAYMENT_FORMAT_SEPARATOR);
    }

    private String getUsdEquivalentString(Optional<BigDecimal> usdEquivalent){
        String usdString;
        if (usdEquivalent.isPresent()){
            usdString = usdEquivalent.get().setScale(2, BigDecimal.ROUND_HALF_EVEN).toPlainString();
        }else{
            usdString = "unknown";
        }
        return StringUtils.join("(USD ", usdString, ")");
    }

    private void validateCurrency(String currency){
        if (CURRENCY_CODE_LENGTH != currency.length() ){
            throw new PaymentStringException("Invalid length of currency code. Currency code is expected to have exactly three characters.");
        }

        if (!StringUtils.isAllUpperCase(currency)){
            throw new PaymentStringException("Invalid case of currency code letters. All currency code letters are expected to be uppercase.");
        }
    }

    private BigDecimal convertToBigDecimal(String amountString){
        if (isNumeric(amountString) || amountString.startsWith("-") && isNumeric(amountString.substring(1))){
            return new BigDecimal(amountString);
        }else{
            throw new PaymentStringException("Invalid amount format. Amount is expected to be integer number." );
        }
    }
}
