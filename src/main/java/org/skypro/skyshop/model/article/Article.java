package org.skypro.skyshop.model.article;

import org.skypro.skyshop.model.search.Searchable;

import java.util.UUID;

public class Article implements Searchable {
    private final UUID id;
    private final String name;
    private final String content;

    public Article(UUID id, String name, String content) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Название статьи не может быть пустым");
        }
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("Содержание статьи не может быть пустым");
        }
        this.id = id;
        this.name = name;
        this.content = content;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String getSearchTerm() {
        return (name + " " + content).toLowerCase();
    }

    @Override
    public String getContentType() {
        return "ARTICLE";
    }

    public String getStringRepresentation() {
        return name + ": " + (content.length() > 50 ? content.substring(0, 50) + "..." : content);
    }

    @Override
    public String toString() {
        return getStringRepresentation();
    }
}