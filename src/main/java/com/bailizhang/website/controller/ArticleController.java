package com.bailizhang.website.controller;

import com.bailizhang.lynxdb.client.connection.LynxDbConnection;
import com.bailizhang.website.core.Message;
import com.bailizhang.website.entity.Article;
import org.springframework.web.bind.annotation.*;

import java.net.ConnectException;
import java.util.UUID;

@RestController
@RequestMapping("articles")
public class ArticleController {
    private final LynxDbConnection lynxDbConnection;

    ArticleController(LynxDbConnection connection) {
        lynxDbConnection = connection;
    }

    @GetMapping("/{id}")
    private Article find(@PathVariable("id") String id) throws ConnectException {
        return lynxDbConnection.find(id, Article.class);
    }

    @PostMapping
    private String create(@RequestBody Article article) throws ConnectException {
        article.setId(generateId());
        int count = 5;

        while(lynxDbConnection.existKey(article)) {
            if(-- count < 0) {
                return Message.FAILED;
            }
            article.setId(generateId());
        }

        // TODO: 应该提供 “如果不存在则插入” 的原子操作
        lynxDbConnection.insert(article);

        return article.getId();
    }

    private String generateId() {
        return System.currentTimeMillis() + UUID.randomUUID().toString();
    }
}
