package prog.ik.btest.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prog.ik.btest.model.Account;
import prog.ik.btest.model.Transaction;
import prog.ik.btest.repository.AccountRepository;
import prog.ik.btest.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TransactionService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transRepository;

    public TransactionService(AccountRepository accountRepository,
                             TransactionRepository transReposototy) {
        this.accountRepository  = accountRepository;
        this.transRepository = transReposototy;
    }

    @Transactional
    public void addTransaction(Transaction transaction) {
        Account account = transaction.getAccount();
        BigDecimal balance;
        if (transaction.isDeposit())    balance = account.getBalance().add(transaction.getAmount());
        else                            balance = account.getBalance().subtract(transaction.getAmount());
        transRepository.save(transaction);
        account.setBalance(balance);
        accountRepository.save(account);
    }

    @Transactional
    public List<Transaction> findByDateRange(Account account, int days) {
        LocalDate toDate = LocalDate.now().plusDays(1);
        LocalDate fromDate = LocalDate.now().minusDays(days);

        return transRepository.findByDateRange(
            account,
            java.sql.Date.valueOf(fromDate),
            java.sql.Date.valueOf(toDate)
        );
    }
}
