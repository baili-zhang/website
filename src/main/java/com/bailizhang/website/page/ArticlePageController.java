package com.bailizhang.website.page;

import com.bailizhang.website.entity.Article;
import com.bailizhang.website.service.ArticleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.ConnectException;

@Controller
@RequestMapping("article")
public class ArticlePageController {
    private final ArticleService articleService;

    ArticlePageController(ArticleService articleServiceImpl) {
        articleService = articleServiceImpl;
    }

    @GetMapping("/{id}")
    private String indexPage(@PathVariable("id") String id, Model model) throws ConnectException {
        Article article = articleService.findArticle(id);
        if(article == null) {
            return "httpError/404";
        }

        model.addAttribute(article);

        return "article/index";
    }

    @GetMapping
    private String createPage() {
        return "article/create";
    }
}
