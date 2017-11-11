package cz.kadrlem.samples.payments;

import cz.kadrlem.samples.payments.service.CurrencyConverter;
import cz.kadrlem.samples.payments.service.impl.CurrencyConverterImpl;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * JUnit test of {@link CurrencyConverterImpl}.
 */
public class CurrencyConverterImplTest {

    private CurrencyConverter currencyConverter = new CurrencyConverterImpl();

    @Test
    public void testConvertAmountToUsd() throws Exception {
        assertEquals(optionalAmount("500"), currencyConverter.convertAmountToUsd("USD", new BigDecimal("500")));
        assertEquals(optionalAmount("112.00"), currencyConverter.convertAmountToUsd("EUR", new BigDecimal("100")));
        assertEquals(optionalAmount("128.00"), currencyConverter.convertAmountToUsd("GBP", new BigDecimal("100")));
        assertEquals(optionalAmount("9.000"), currencyConverter.convertAmountToUsd("JPY", new BigDecimal("1000")));
        assertEquals(Optional.empty(), currencyConverter.convertAmountToUsd("XYZ", new BigDecimal("100")));
    }

    private Optional optionalAmount(String amount){
        return Optional.of(new BigDecimal(amount));
    }
}