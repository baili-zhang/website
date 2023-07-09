package com.bailizhang.website.entity;

import com.bailizhang.lynxdb.client.annotation.LynxDbColumn;
import com.bailizhang.lynxdb.client.annotation.LynxDbColumnFamily;
import com.bailizhang.lynxdb.client.annotation.LynxDbKey;
import com.bailizhang.lynxdb.client.annotation.LynxDbMainColumn;
import lombok.Data;

@Data
@LynxDbColumnFamily("user-session")
public class UserSession {
    public static final String USERNAME_COOKIE = "username";
    public static final String SESSION_ID_COOKIE = "session-id";

    @LynxDbKey
    private String username;

    @LynxDbMainColumn
    @LynxDbColumn
    private String sessionId;
}
