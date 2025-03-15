package prog.ik.btest.service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class Utility {
    public static String getRandomIban() {
        return "UA" + RandomStringUtils.random(27, false, true);
    }

    public static User getCurrentUser() {
        return (User) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }
}
