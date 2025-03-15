package prog.ik.btest.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prog.ik.btest.model.AuthUser;
import prog.ik.btest.repository.AuthUserRepository;

@Service
public class AuthUserService {

    private final AuthUserRepository authUserRepository;

    public AuthUserService(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    @Transactional(readOnly = true)
    public AuthUser findByLogin(String login) {
        return authUserRepository.findByLogin(login);
    }

    @Transactional
    public boolean addUser(String username,
                           String passHash,
                           String email) {
        if (authUserRepository.existsByLogin(username) || authUserRepository.existsByEmail(email))
            return false;

        AuthUser user = new AuthUser(username, passHash, email);
        authUserRepository.save(user);

        return true;
    }
}
