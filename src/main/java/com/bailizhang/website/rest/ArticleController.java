package com.bailizhang.website.rest;

import com.bailizhang.lynxdb.client.connection.LynxDbConnection;
import com.bailizhang.website.entity.Article;
import com.bailizhang.website.result.CreateArticleResult;
import com.bailizhang.website.result.Result;
import com.bailizhang.website.utils.IdUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.ConnectException;

@RestController
@RequestMapping("article")
public class ArticleController {
    private final LynxDbConnection lynxDbConnection;

    ArticleController(LynxDbConnection connection) {
        lynxDbConnection = connection;
    }

    @PostMapping
    private Result create(@RequestBody Article article) throws ConnectException {
        int count = 5;

        do {
            if (--count < 0) {
                return new Result(false);
            }

            article.setId(IdUtils.generateId());
        } while (!lynxDbConnection.insertIfNotExisted(article));

        CreateArticleResult result = new CreateArticleResult();
        result.setSuccess(true);
        result.setId(article.getId());
        return result;
    }
}
