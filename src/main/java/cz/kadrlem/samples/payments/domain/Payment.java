package cz.kadrlem.samples.payments.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * JPA entity represents one payment.
 */
@Entity
public class Payment {

    @Id
    @GeneratedValue
    private Long id;

    private String currencyCode;
    private BigDecimal amount;

    public Payment() {
        //implicit constructor for hibernate
    }

    public Payment(String currencyCode, BigDecimal amount) {
        this.currencyCode = currencyCode;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Payment{");
        sb.append("id=").append(id);
        sb.append(", currencyCode='").append(currencyCode).append('\'');
        sb.append(", amount=").append(amount);
        sb.append('}');
        return sb.toString();
    }
}
