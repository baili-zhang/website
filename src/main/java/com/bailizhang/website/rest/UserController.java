package com.bailizhang.website.rest;

import com.bailizhang.website.entity.User;
import com.bailizhang.website.result.Result;
import com.bailizhang.website.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.ConnectException;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userServiceImpl) {
        userService = userServiceImpl;
    }

    @PostMapping
    private Result create(@RequestBody User user) throws ConnectException {
        boolean success = userService.create(user);
        return new Result(success);
    }
}
