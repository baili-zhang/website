package com.bailizhang.website.service.impl;

import com.bailizhang.lynxdb.client.connection.LynxDbConnection;
import com.bailizhang.website.entity.Article;
import com.bailizhang.website.service.ArticleService;
import org.commonmark.Extension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Service;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        List<Extension> extensions = List.of(TablesExtension.create());
        Parser parser = Parser.builder().extensions(extensions).build();
        Node document = parser.parse(article.getContent());
        HtmlRenderer renderer = HtmlRenderer.builder().extensions(extensions).build();
        String newContent = renderer.render(document);
        article.setContent(newContent);

        return article;
    }
}
