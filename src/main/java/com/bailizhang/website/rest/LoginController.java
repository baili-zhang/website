package com.bailizhang.website.rest;

import com.bailizhang.website.entity.UserSession;
import com.bailizhang.website.param.LoginParam;
import com.bailizhang.website.result.Result;
import com.bailizhang.website.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.ConnectException;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userServiceImpl) {
        userService = userServiceImpl;
    }

    @PostMapping
    private Result login(
            @RequestBody @Validated LoginParam param,
            HttpServletResponse response
    ) throws ConnectException {
        UserSession userSession = userService.login(param);
        if(userSession == null) {
            return new Result(false);
        }

        Cookie usernameCookie = new Cookie("username", userSession.getUsername());
        Cookie sessionIdCookie = new Cookie("session-id", userSession.getSessionId());
        response.addCookie(usernameCookie);
        response.addCookie(sessionIdCookie);

        return new Result(true);
    }
}
