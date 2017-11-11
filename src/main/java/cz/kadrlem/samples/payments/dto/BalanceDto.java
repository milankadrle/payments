package cz.kadrlem.samples.payments.dto;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;

/**
 * DTO to transfer balances to "console view".
 */
public class BalanceDto {

    private String currencyCode;
    private BigDecimal balance;
    private Optional<BigDecimal> usdEquivalent;

    public BalanceDto(String currencyCode, BigDecimal balance, Optional<BigDecimal> usdEquivalent) {
        this.currencyCode = currencyCode;
        this.balance = balance;
        this.usdEquivalent = usdEquivalent;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Optional<BigDecimal> getUsdEquivalent() {
        return usdEquivalent;
    }

    public void setUsdEquivalent(Optional<BigDecimal> usdEquivalent) {
        this.usdEquivalent = usdEquivalent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        BalanceDto that = (BalanceDto) o;
        return Objects.equals(getCurrencyCode(), that.getCurrencyCode()) &&
                Objects.equals(getBalance(), that.getBalance()) &&
                Objects.equals(getUsdEquivalent(), that.getUsdEquivalent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCurrencyCode(), getBalance(), getUsdEquivalent());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BalanceDto{");
        sb.append("currencyCode='").append(currencyCode).append('\'');
        sb.append(", balance=").append(balance);
        sb.append(", usdEquivalent=").append(usdEquivalent);
        sb.append('}');
        return sb.toString();
    }
}
