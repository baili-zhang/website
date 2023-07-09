package com.bailizhang.website.entity;

import com.bailizhang.lynxdb.client.annotation.LynxDbColumn;
import com.bailizhang.lynxdb.client.annotation.LynxDbColumnFamily;
import com.bailizhang.lynxdb.client.annotation.LynxDbKey;
import com.bailizhang.lynxdb.client.annotation.LynxDbMainColumn;
import lombok.Data;

@Data
@LynxDbColumnFamily("user")
public class User {
    @LynxDbKey
    private String username;

    @LynxDbMainColumn
    @LynxDbColumn
    private String password;
}
