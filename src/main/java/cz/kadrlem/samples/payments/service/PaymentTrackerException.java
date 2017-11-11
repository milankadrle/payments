package cz.kadrlem.samples.payments.service;

/**
 * Base payment tracker exception.
 */
public class PaymentTrackerException extends RuntimeException{

    /**
     * Constructs new PaymentStringException with the specified detail message.
     * @param message detail message
     */
    public PaymentTrackerException(String message) {
        super(message);
    }

    /**
     * Constructs new PaymentStringException with the specified cause exception.
     * @param cause cause exception
     */
    public PaymentTrackerException(Throwable cause) {
        super(cause);
    }
}
