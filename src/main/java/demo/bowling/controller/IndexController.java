package demo.bowling.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private final String PAGE = "index";

    @GetMapping("/")
    public String index() {
        return PAGE;
    }
}
