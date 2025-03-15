package prog.ik.btest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import prog.ik.btest.service.AuthUserService;

@Controller
public class AuthController {

    private final AuthUserService authUserService;

    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthUserService authUserService, PasswordEncoder passwordEncoder) {
        this.authUserService = authUserService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/regform")
    public String registerAuthUser(Model model) {
        return "register";
    }

    @PostMapping("/register")
    public String saveAuthUser(@RequestParam String username,
                            @RequestParam String password,
                            @RequestParam String email,
                            Model model) {
        String passHash = passwordEncoder.encode(password);
        if (!authUserService.addUser(username, passHash, email)) {
            model.addAttribute("exists", true);
            model.addAttribute("username", username);
            model.addAttribute("email", email);

            return "register";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginAuthUser(Model model) {
        return "login";
    }


    @GetMapping("/logout")
    public String logoutPage() {
        return "redirect:/regform";
    }
}
