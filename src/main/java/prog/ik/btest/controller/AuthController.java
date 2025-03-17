package prog.ik.btest.controller;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prog.ik.btest.model.AuthUser;
import prog.ik.btest.service.AuthUserService;
import prog.ik.btest.service.Constants;
import prog.ik.btest.service.EmailService;
import prog.ik.btest.service.Utility;

import java.util.Objects;

@Controller
public class AuthController {

    private final AuthUserService authUserService;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    public AuthController(AuthUserService authUserService, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.authUserService = authUserService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @GetMapping("/regform")
    public String registerAuthUser(Model model) {
        return "register";
    }

    @PostMapping("/register")
    public String saveAuthUser(@RequestParam String username,
                            @RequestParam String password,
                            @RequestParam String password1,
                            @RequestParam String email,
                            Model model) {
        try {
            if (!password.equals(password1)) {
                throw new IllegalArgumentException("Passwords aren't equal");
            }
            if (authUserService.findByLogin(username) != null) {
                throw new RuntimeException("User already exists");
            }
            String passHash = passwordEncoder.encode(password);
            if (!authUserService.addUser(username, passHash, email)) {
                throw new RuntimeException("User wasn't created");
            }
            AuthUser authUser = authUserService.findByLogin(username);
            if (Objects.isNull(authUser)) {
                throw new RuntimeException("User is not found");
            }
            sendEmail(authUser, model);

            return "redirect:/";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("username", username);
            model.addAttribute("email", email);

            return "register";
        }
    }

    @GetMapping("/login")
    public String loginAuthUser(Model model) {
        return "login";
    }


    @GetMapping("/logout")
    public String logoutPage() {
        return "redirect:/regform";
    }

    private void sendEmail(AuthUser authUser, Model model) {
        try {
            emailService.sendSimpleMessage(
                    authUser.getEmail(),
                    Constants.REGISTRATION_SUBJECT,
                    String.format(Constants.REGISTRATION_MESSAGE, authUser.getUsername())
            );
            model.addAttribute("sendmailResult", "Sent successfully");
        } catch (Exception ex) {
            model.addAttribute("sendmailResult", "Error " + ex.getMessage());
        }

        model.addAttribute("email", "nabstr16@gmail.com");
    }
}
