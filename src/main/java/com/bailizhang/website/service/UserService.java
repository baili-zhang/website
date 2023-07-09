package com.bailizhang.website.service;

import com.bailizhang.website.entity.User;
import com.bailizhang.website.entity.UserSession;
import com.bailizhang.website.param.LoginParam;

import java.net.ConnectException;

public interface UserService {
    boolean create(User user) throws ConnectException;

    UserSession login(LoginParam param) throws ConnectException;
}
