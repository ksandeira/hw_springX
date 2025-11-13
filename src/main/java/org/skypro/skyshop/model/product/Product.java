package org.skypro.skyshop.model.product;

import org.skypro.skyshop.model.search.Searchable;

import java.util.UUID;

public abstract class Product implements Searchable {
    private final UUID id;
    private final String name;
    private final int cost;

    public Product(UUID id, String name, int cost) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название товара не может быть пустым");
        }
        if (cost <= 0) {
            throw new IllegalArgumentException("Стоимость товара должна быть положительной");
        }
        this.id = id;
        this.name = name;
        this.cost = cost;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public String getSearchTerm() {
        return name.toLowerCase();
    }

    @Override
    public String getContentType() {
        return "PRODUCT";
    }

    public String getStringRepresentation() {
        return name + " - " + cost + " руб.";
    }

    @Override
    public String toString() {
        return getStringRepresentation();
    }
}