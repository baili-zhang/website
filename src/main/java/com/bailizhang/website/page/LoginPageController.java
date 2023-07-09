package com.bailizhang.website.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginPageController {
    @GetMapping("/login")
    private String error401() {
        return "login";
    }
}
