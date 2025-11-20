package org.skypro.skyshop.service;

import org.skypro.skyshop.model.search.Searchable;
import org.skypro.skyshop.model.product.*;
import org.skypro.skyshop.model.article.Article;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StorageService {

    private final Map<UUID, Product> products;
    private final Map<UUID, Article> articles;

    public StorageService() {
        this.products = new HashMap<>();
        this.articles = new HashMap<>();
        initializeTestData();
    }

    private void initializeTestData() {
        Product dogFood = new SimpleProduct(UUID.randomUUID(), "Корм для собак сухой", 4000);
        Product catFood = new SimpleProduct(UUID.randomUUID(), "Корм для стерилизованных кошек", 2500);
        Product aquarium = new DiscountedProduct(UUID.randomUUID(), "Аквариум 50л с подсветкой", 8000, 10);
        Product parrotCage = new DiscountedProduct(UUID.randomUUID(), "Клетка для попугаев большая", 6000, 15);
        Product hamsterWheel = new FixPriceProduct(UUID.randomUUID(), "Беговое колесо для хомяка");
        Product dogCollar = new FixPriceProduct(UUID.randomUUID(), "Кожаный ошейник для собак");
        Product catTree = new SimpleProduct(UUID.randomUUID(), "Игровой комплекс для кошек", 8950);
        Product fishFood = new SimpleProduct(UUID.randomUUID(), "Корм для рыб", 650);
        Product parrotFood = new SimpleProduct(UUID.randomUUID(), "Корм для попугаев", 750);

        addProduct(dogFood);
        addProduct(catFood);
        addProduct(aquarium);
        addProduct(parrotCage);
        addProduct(hamsterWheel);
        addProduct(dogCollar);
        addProduct(catTree);
        addProduct(fishFood);
        addProduct(parrotFood);

        Article dogCareArticle = new Article(UUID.randomUUID(),
                "Содержание собак",
                "Правильный уход за собаками включает регулярное кормление, ежедневные прогулки и своевременные ветеринарные осмотры.");
        Article catCareArticle = new Article(UUID.randomUUID(),
                "Содержание кошек",
                "Кошкам необходим правильно подобранный корм, игровые комплексы, а так же нуждаются в регулярном уходе.");
        Article fishArticle = new Article(UUID.randomUUID(),
                "Содержание рыб",
                "Выбор аквариума, уход за рыбками и поддержание чистоты воды");
        Article parrotArticle = new Article(UUID.randomUUID(),
                "Содержание попугаев",
                "Правильное питание попугаев, выбор клетки, а так же регулярный уход за ними");

        addArticle(dogCareArticle);
        addArticle(catCareArticle);
        addArticle(fishArticle);
        addArticle(parrotArticle);
    }

    private void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    private void addArticle(Article article) {
        articles.put(article.getId(), article);
    }

    public List<Product> getAllProducts() {
        return products.values().stream().toList();
    }

    public List<Article> getAllArticles() {
        return articles.values().stream().toList();
    }

    public Collection<Searchable> getAllSearchables() {
        List<Searchable> searchables = new ArrayList<>();
        searchables.addAll(products.values());
        searchables.addAll(articles.values());
        return Collections.unmodifiableCollection(searchables);
    }

    public Optional<Product> getProductById(UUID id) {
        return Optional.ofNullable(products.get(id));
    }
}