package com.bailizhang.website.page;

import com.bailizhang.website.entity.Article;
import com.bailizhang.website.service.ArticleService;
import com.bailizhang.website.service.WebsiteConfigService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.ConnectException;

@Controller
public class HomePageController {
    private final WebsiteConfigService websiteConfigService;
    private final ArticleService articleService;

    public HomePageController(
            WebsiteConfigService websiteConfigServiceImpl,
            ArticleService articleServiceImpl
    ) {
        websiteConfigService = websiteConfigServiceImpl;
        articleService = articleServiceImpl;
    }

    @GetMapping("/")
    private String homePage(Model model) throws ConnectException {
        String articleId = websiteConfigService.findHomePageArticleId();
        if(articleId == null) {
            return "httpError/404";
        }

        Article article = articleService.findArticle(articleId);
        if(article == null) {
            return "httpError/404";
        }

        model.addAttribute(article);
        return "article/index";
    }
}
