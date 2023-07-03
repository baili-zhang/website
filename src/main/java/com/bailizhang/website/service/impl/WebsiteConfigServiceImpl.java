package com.bailizhang.website.service.impl;

import com.bailizhang.lynxdb.client.connection.LynxDbConnection;
import com.bailizhang.lynxdb.core.common.G;
import com.bailizhang.website.service.WebsiteConfigService;
import org.springframework.stereotype.Service;

import java.net.ConnectException;

@Service
public class WebsiteConfigServiceImpl implements WebsiteConfigService {
    private static final String WEBSITE_CONFIG_COLUMN_FAMILY = "website-config";
    private static final String HOME_CONFIG_COLUMN = "home-config";
    private static final byte[] ARTICLE_ID = G.I.toBytes("article-id");

    private final LynxDbConnection lynxDbConnection;

    public WebsiteConfigServiceImpl(LynxDbConnection connection) {
        lynxDbConnection = connection;
    }

    @Override
    public String findHomePageArticleId() throws ConnectException {
        byte[] articleId = lynxDbConnection.find(
                ARTICLE_ID,
                WEBSITE_CONFIG_COLUMN_FAMILY,
                HOME_CONFIG_COLUMN
        );

        return G.I.toString(articleId);
    }

    @Override
    public void setHomePageArticleId(String articleId) throws ConnectException {
        lynxDbConnection.insert(
                ARTICLE_ID,
                WEBSITE_CONFIG_COLUMN_FAMILY,
                HOME_CONFIG_COLUMN,
                G.I.toBytes(articleId)
        );
    }
}
