package com.bailizhang.website.service.impl;

import com.bailizhang.lynxdb.client.connection.LynxDbConnection;
import com.bailizhang.website.entity.Article;
import com.bailizhang.website.service.ArticleService;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Service;

import java.net.ConnectException;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final LynxDbConnection lynxDbConnection;

    public ArticleServiceImpl(LynxDbConnection connection) {
        lynxDbConnection = connection;
    }

    @Override
    public Article findArticle(String id) throws ConnectException {
        Article article = lynxDbConnection.find(id, Article.class);

        if(article == null) {
            return null;
        }

        Parser parser = Parser.builder().build();
        Node document = parser.parse(article.getContent());
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String newContent = renderer.render(document);
        article.setContent(newContent);

        return article;
    }
}
