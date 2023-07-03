package com.bailizhang.website.rest;

import com.bailizhang.website.core.Message;
import com.bailizhang.website.param.HomeConfigParam;
import com.bailizhang.website.service.WebsiteConfigService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.ConnectException;

@RestController
@RequestMapping("websiteConfig")
public class WebsiteConfigController {
    private final WebsiteConfigService websiteConfigService;

    public WebsiteConfigController(WebsiteConfigService websiteConfigServiceImpl) {
        websiteConfigService = websiteConfigServiceImpl;
    }

    @PostMapping("/homeArticleId")
    private String setHomeArticleId(@RequestBody HomeConfigParam param) throws ConnectException {
        if(param.getArticleId() == null) {
            return Message.FAILED;
        }

        websiteConfigService.setHomePageArticleId(param.getArticleId());

        return Message.OK;
    }

}
