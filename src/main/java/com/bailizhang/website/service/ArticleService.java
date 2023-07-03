package com.bailizhang.website.service;

import com.bailizhang.website.entity.Article;

import java.net.ConnectException;

public interface ArticleService {
    Article findArticle(String id) throws ConnectException;
}
