package cz.kadrlem.samples.payments;

import cz.kadrlem.samples.payments.domain.Payment;
import cz.kadrlem.samples.payments.dto.BalanceDto;
import cz.kadrlem.samples.payments.repository.PaymentRepository;
import cz.kadrlem.samples.payments.service.impl.CurrencyConverterImpl;
import cz.kadrlem.samples.payments.service.impl.PaymentStringConverterImpl;
import cz.kadrlem.samples.payments.service.impl.PaymentTrackerServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * JUnit test of {@link PaymentTrackerServiceImpl}. Uses mocking with Mockito.
 */
public class PaymentTrackerServiceImplTest {

    @InjectMocks
    private PaymentTrackerServiceImpl paymentTrackerService;

    @Mock
    private PaymentRepository paymentRepository;

    @Spy
    private CurrencyConverterImpl currencyConverter;

    @Spy
    private PaymentStringConverterImpl paymentStringConverter;

    @Before
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSavePayment() throws Exception {
        Payment payment = new Payment("USD", new BigDecimal("500"));
        paymentTrackerService.savePayment(payment);

        verify(paymentRepository).save(payment);
    }

    @Test
    public void testGetCurrencyBalances() throws Exception {
        List<Payment> payments = new ArrayList<>();
        payments.add(new Payment("USD", new BigDecimal("100")));
        payments.add(new Payment("EUR", new BigDecimal("50")));
        payments.add(new Payment("USD", new BigDecimal("100")));
        payments.add(new Payment("CZK", new BigDecimal("200")));
        payments.add(new Payment("EUR", new BigDecimal("-10")));

        doReturn(payments).when(paymentRepository).findAll();

        List<BalanceDto> balances = new ArrayList<>();
        balances.add(new BalanceDto("CZK", new BigDecimal("200"), Optional.empty()));
        balances.add(new BalanceDto("EUR", new BigDecimal("40"), Optional.of(new BigDecimal("44.80"))));
        balances.add(new BalanceDto("USD", new BigDecimal("200"), Optional.of(new BigDecimal("200"))));

        assertEquals(balances, paymentTrackerService.getCurrencyBalances());
    }

    @Test
    public void testProcessInputFile() throws Exception{
        String filePath = getClass().getResource("/testInput.txt").getPath();
        paymentTrackerService.processInputFile(filePath);

        verify(paymentRepository, times(4)).save(any(Payment.class));
    }
}
