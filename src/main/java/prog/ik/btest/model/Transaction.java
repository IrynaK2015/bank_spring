package prog.ik.btest.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=29, nullable = true)
    private String iban;

    @Column(length=1, nullable = false)
    private String operation;

    @Column(precision=13, scale=2, nullable = false)
    private BigDecimal amount;

    @Column(length=255, nullable = false)
    private String comment;

    @CreationTimestamp
    private Date created;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Transaction() {}

    public Transaction(Account account, String iban, String operation, BigDecimal amount, String comment) {
        this.account = account;
        this.iban = iban;
        this.operation = operation;
        this.amount = amount;
        this.comment = comment;
    }

    public Transaction(Account account, String operation, BigDecimal amount, String comment) {
        this.account = account;
        this.operation = operation;
        this.amount = amount;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getCreated() {
        return created;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getOperation() {
        return operation;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public boolean isDeposit() {
        return operation.equals("+");
    }

    public boolean isWithdraw() {
        return operation.equals("-");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(account.getIban());
        sb.append(" : ");
        sb.append(amount);
        sb.append(" ");
        sb.append(account.getCurrency().getCode());
        if (operation.equals("+"))  sb.append(" income from ");
        else                        sb.append(" withdrow to ");
        if (Objects.equals(iban, null)) sb.append("cash");
        else                                sb.append(iban);

        return sb.toString();
    };
}
