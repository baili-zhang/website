package com.bailizhang.website.page;

import com.bailizhang.website.entity.Article;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.ConnectException;

@Controller
public class HttpErrorPageController {
    @GetMapping("/401")
    private String error401() {
        return "httpError/401";
    }

    @GetMapping("/404")
    private String error404() {
        return "httpError/404";
    }
}
