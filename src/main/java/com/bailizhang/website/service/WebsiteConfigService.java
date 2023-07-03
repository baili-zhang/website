package com.bailizhang.website.service;

import java.net.ConnectException;

public interface WebsiteConfigService {
    String findHomePageArticleId() throws ConnectException;
    void setHomePageArticleId(String articleId) throws ConnectException;
}
