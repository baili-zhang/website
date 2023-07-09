package com.bailizhang.website.entity;

import com.bailizhang.lynxdb.client.annotation.LynxDbColumn;
import com.bailizhang.lynxdb.client.annotation.LynxDbColumnFamily;
import com.bailizhang.lynxdb.client.annotation.LynxDbKey;
import com.bailizhang.lynxdb.client.annotation.LynxDbMainColumn;
import lombok.Data;

@Data
@LynxDbColumnFamily("article")
public class Article {
    @LynxDbKey
    private String id;

    @LynxDbColumn
    private String keywords;

    @LynxDbMainColumn
    @LynxDbColumn
    private String content;
}
