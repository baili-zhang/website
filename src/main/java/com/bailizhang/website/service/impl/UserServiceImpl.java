package com.bailizhang.website.service.impl;

import com.bailizhang.lynxdb.client.connection.LynxDbConnection;
import com.bailizhang.website.entity.User;
import com.bailizhang.website.entity.UserSession;
import com.bailizhang.website.param.LoginParam;
import com.bailizhang.website.service.UserService;
import com.bailizhang.website.utils.IdUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.ConnectException;

@Service
public class UserServiceImpl implements UserService {

    private final LynxDbConnection lynxDbConnection;

    public UserServiceImpl(LynxDbConnection connection) {
        lynxDbConnection = connection;
    }

    @Override
    public boolean create(User user) throws ConnectException {
        if(!StringUtils.hasText(user.getUsername())
                || !StringUtils.hasText(user.getPassword())) {
            return false;
        }

        return lynxDbConnection.insertIfNotExisted(user);
    }

    @Override
    public UserSession login(LoginParam param) throws ConnectException {
        if(!StringUtils.hasText(param.getUsername())
                || !StringUtils.hasText(param.getPassword())) {
            return null;
        }

        User user = lynxDbConnection.find(param.getUsername(), User.class);
        if(user == null || !user.getPassword().equals(param.getPassword())) {
            return null;
        }

        UserSession userSession = new UserSession();
        userSession.setUsername(user.getUsername());
        userSession.setSessionId(IdUtils.generateId());

        long timeout = System.currentTimeMillis() + 5 * 24 * 60 * 60 * 1000;
        lynxDbConnection.insert(userSession, timeout);

        return userSession;
    }
}
