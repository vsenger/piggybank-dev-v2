package piggybank.api.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Transactions")
public class Transaction extends PanacheEntity {
    LocalDate timeStamp;

    BigDecimal value;
    String description;
    int balanceOrder;
    @ManyToOne
    Category category;
    @ManyToOne
    Account account;
    public Transaction(){}

    public Transaction(LocalDate timeStamp, BigDecimal value, String description, Category category, Account account) {
        this.timeStamp = timeStamp;
        this.value = value;
        this.description = description;
        this.category=category;
        this.account=account;
    }

    public int getBalanceOrder() {
        return balanceOrder;
    }

    public Category getCategory() {
        return category;
    }

    public String getTimeStampStr() {
        return timeStamp.toString();
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public Account getAccount() { return account; }
}
