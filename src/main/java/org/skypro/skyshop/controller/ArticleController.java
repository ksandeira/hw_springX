package org.skypro.skyshop.controller;

import org.skypro.skyshop.model.article.Article;
import org.skypro.skyshop.service.StorageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleController {

    private final StorageService storageService;

    public ArticleController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/articles")
    public List<Article> getAllArticles() {
        return (List<Article>) storageService.getAllArticles();
    }

}
