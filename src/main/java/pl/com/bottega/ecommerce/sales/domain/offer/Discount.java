package pl.com.bottega.ecommerce.sales.domain.offer;

import java.util.Objects;

public class Discount {
    private String cause;
    private Money value;

    public Discount(String cause, Money value) {
        this.cause = cause;
        this.value = value;
    }

    public String getCause() {
        return cause;
    }

    public Money getValue() {
        return value;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public void setValue(Money value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Discount other = (Discount) obj;
        return Objects.equals(cause, other.getCause())
                && Objects.equals(value, other.getValue());
    }
}
