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

    @LynxDbMainColumn
    @LynxDbColumn
    private String title;

    @LynxDbColumn
    private String content;
}
