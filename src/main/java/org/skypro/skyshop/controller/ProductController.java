package org.skypro.skyshop.controller;

import org.skypro.skyshop.model.product.Product;
import org.skypro.skyshop.service.StorageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    private final StorageService storageService;

    public ProductController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return storageService.getAllProducts();
    }
}