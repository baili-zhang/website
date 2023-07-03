package com.bailizhang.website.rest;

import com.bailizhang.lynxdb.client.connection.LynxDbConnection;
import com.bailizhang.website.core.Message;
import com.bailizhang.website.entity.Article;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.ConnectException;
import java.util.UUID;

@RestController
@RequestMapping("article")
public class ArticleController {
    private final LynxDbConnection lynxDbConnection;

    ArticleController(LynxDbConnection connection) {
        lynxDbConnection = connection;
    }

    @PostMapping
    private String create(@RequestBody Article article) throws ConnectException {
        int count = 5;

        do {
            if(-- count < 0) {
                return Message.FAILED;
            }

            article.setId(generateId());
        } while (!lynxDbConnection.insertIfNotExisted(article));

        return article.getId();
    }

    private String generateId() {
        return System.currentTimeMillis() + UUID.randomUUID().toString();
    }
}
