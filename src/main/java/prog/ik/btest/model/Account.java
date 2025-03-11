package prog.ik.btest.model;

import jakarta.persistence.*;
import prog.ik.btest.service.Utility;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=29, nullable = false)
    private String iban;

    @Column(precision=13, scale=2, nullable = false)
    private BigDecimal balance;

    @ManyToOne()
    @JoinColumn(name = "currency_id")
    private Currencyrate currency;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    final private List<Transaction> transactions = new ArrayList<>();

    public Account() {}

    public Account(Client client, Currencyrate currency) {
        this.client = client;
        this.iban = Utility.getRandomIban();
        this.currency = currency;
        this.balance = BigDecimal.ZERO;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Currencyrate getCurrency() {
        return currency;
    }

    public void setCurrency(Currencyrate currency) {
        this.currency = currency;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = (balance);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void addTransaction(Transaction trans) {
        if (!transactions.contains(trans)) {
            transactions.add(trans);
            trans.setAccount(this);
        }
    }


    @Override
    public String toString() {
        return String.format(
                "ID = %d, IBAN = %s, currency %s, balance = %.2f %s",
                id, iban, currency.getName(), balance, currency.getCode()
        );
    }
}
