package cz.kadrlem.samples.payments;

import cz.kadrlem.samples.payments.domain.Payment;
import cz.kadrlem.samples.payments.dto.BalanceDto;
import cz.kadrlem.samples.payments.service.PaymentStringConverter;
import cz.kadrlem.samples.payments.service.PaymentStringException;
import cz.kadrlem.samples.payments.service.impl.PaymentStringConverterImpl;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

/**
 * JUnit test of {@link PaymentStringConverterImpl}.
 */
public class PaymentStringConverterImplTest {

    private PaymentStringConverter paymentStringConverter = new PaymentStringConverterImpl();

    @Test
    public void testToPayment() throws Exception {
        assertPayment(paymentStringConverter.toPayment("USD 500"), "USD", new BigDecimal("500"));
        assertPayment(paymentStringConverter.toPayment("\tEUR -500 "), "EUR", new BigDecimal("-500"));
        assertPayment(paymentStringConverter.toPayment("  CZK 100  "), "CZK", new BigDecimal("100"));
    }

    @Test(expected = PaymentStringException.class)
    public void testToPaymentInvalidCurrencyCode() throws Exception {
        paymentStringConverter.toPayment("US 500");
    }

    @Test(expected = PaymentStringException.class)
    public void testToPaymentInvalidAmount() throws Exception {
        paymentStringConverter.toPayment("USD 500.5");
    }

    @Test(expected = PaymentStringException.class)
    public void testToPaymentInvalidFormat() throws Exception {
        paymentStringConverter.toPayment("500EUR");
    }

    @Test(expected = PaymentStringException.class)
    public void testToPaymentInvalidCase() throws Exception {
        paymentStringConverter.toPayment("EUr 100");
    }

    @Test
    public void testFromBalanceDto() throws Exception {
        assertEquals("EUR 100 (USD 110.00)", paymentStringConverter.fromBalanceDto(new BalanceDto("EUR", new BigDecimal("100"), Optional.of(new BigDecimal("110")))));
        assertEquals("CZK 100 (USD unknown)", paymentStringConverter.fromBalanceDto(new BalanceDto("CZK", new BigDecimal("100"), Optional.empty())));
    }

    private void assertPayment(Payment payment, String currencyCode, BigDecimal amount){
        assertEquals(amount, payment.getAmount());
        assertEquals(currencyCode, payment.getCurrencyCode());
    }

}