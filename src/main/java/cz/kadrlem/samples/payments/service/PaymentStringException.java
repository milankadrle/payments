package cz.kadrlem.samples.payments.service;

/**
 * Thrown when there is an invalid payment string detected.
 */
public class PaymentStringException extends PaymentTrackerException {

    /**
     * Constructs new PaymentStringException with the specified detail message.
     * @param message detail message
     */
    public PaymentStringException(String message) {
        super(message);
    }
}
