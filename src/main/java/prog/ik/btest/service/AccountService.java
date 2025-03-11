package prog.ik.btest.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prog.ik.btest.model.Account;
import prog.ik.btest.repository.AccountRepository;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Transactional(readOnly=true)
    public Account findAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }
}
