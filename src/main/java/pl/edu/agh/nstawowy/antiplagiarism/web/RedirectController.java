package pl.edu.agh.nstawowy.antiplagiarism.web;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class RedirectController {
    @RequestMapping("/")
    public String defaultPage() {
        return "redirect:/app/index.html";
    }
}
