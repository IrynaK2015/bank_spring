package prog.ik.btest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import prog.ik.btest.service.Utility;

@Controller
public class WebController {

    public WebController() {
    }

    @GetMapping("/")
    public String onIndex(Model model) {

        return "index";
    }

    @ModelAttribute("username")
    public String getUsername() {
        return Utility.getCurrentUser().getUsername();
    }
}
