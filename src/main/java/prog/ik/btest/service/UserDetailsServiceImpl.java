package prog.ik.btest.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import prog.ik.btest.model.AuthUser;

import java.util.Arrays;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AuthUserService authUserService;

    public UserDetailsServiceImpl(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        AuthUser authUser = authUserService.findByLogin(login);
        if (authUser == null)
            throw new UsernameNotFoundException(login + " not found");

        List<GrantedAuthority> roles = Arrays.asList(
                new SimpleGrantedAuthority(Constants.ROLE_MANAGER)
        );

        return new User(authUser.getUsername(), authUser.getPassword(), roles);
    }
}