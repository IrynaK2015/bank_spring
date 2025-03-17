package prog.ik.btest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import prog.ik.btest.model.Account;
import prog.ik.btest.model.Transaction;
import prog.ik.btest.service.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.List;


@Controller
public class TransactionController {
    final private TransactionService transactionService;
    final private AccountService accountService;

    public TransactionController(TransactionService transactionService,
                                 AccountService accountService) {
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @GetMapping(value="/account/{id}/transaction/new")
    public String cashTransactionAdd(@PathVariable(value = "id") long accountId, Model model) {
        Account account = accountService.findAccountById(accountId);
        if (Objects.isNull(account)) {
            model.addAttribute("error", "Account not found");

            return "clients";
        }
        model.addAttribute("account", account);

        return "transaction";
    }

    @GetMapping("/account/{id}/statement/{days}")
    public String getStatement(@PathVariable("id") Long accountId,
                               @PathVariable Integer days,
                                Model model) {
        Account account = accountService.findAccountById(accountId);
        if (Objects.isNull(account)) {
            model.addAttribute("error", "Account not found");

            return "redirect:/clients";
        }

        if (days <= 0) days = Constants.STATEMENT_DAYS_DEDAULT;
        List<Transaction> transactions = transactionService.findByDateRange(account, days);
        if (transactions.size() == 0) {
            model.addAttribute("error", "No transactions found");

            return "redirect:/clients";
        }
        model.addAttribute("account", account);
        model.addAttribute("transactions", transactions);

        return "statement";
    }

    @PostMapping(value="/transaction/add")
    public String cashTransactionAdd(@RequestParam Long account_id,
                                     @RequestParam String operation,
                                     @RequestParam BigDecimal amount,
                                     @RequestParam String comment,
                                                 Model model) {

        Account account = accountService.findAccountById(account_id);
        if (Objects.isNull(account)) {
            model.addAttribute("error", "Account not found");

            return "clients";
        }

        try {
            Transaction trans = initTransaction(account, operation, amount, comment);
            transactionService.addTransaction(trans);

            return "redirect:/client/" + account.getClient().getId();

        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());

            return "transaction";
        }
    }

    private Transaction initTransaction(Account account, String oper, BigDecimal amount, String comment) {
        Transaction trans;
        switch (oper) {
            case "+":
                trans = new Transaction(account,"+", amount, comment);
                break;
            case "-":
                trans = new Transaction(account,"-", amount, comment);
                isWithdrowAllowed(trans);
                break;
            default:
                throw new RuntimeException("Incorrect operation");
        }

        return trans;
    }

    @ModelAttribute("username")
    public String getUsername() {
        return Utility.getCurrentUser().getUsername();
    }


    private void isWithdrowAllowed(Transaction trans) {
        if (trans.getAccount().getBalance().compareTo(trans.getAmount()) <= 0) {
            throw new RuntimeException("Your balance is less then requested value");
        }
    }
}
