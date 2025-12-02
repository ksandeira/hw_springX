package org.skypro.skyshop.service;

import org.skypro.skyshop.model.search.Searchable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private final StorageService storageService;

    public SearchService(StorageService storageService) {
        this.storageService = storageService;
    }

    public List<Searchable> search(String term) {
        if (term == null || term.trim().isEmpty()) {
            return List.of();
        }

        String searchTerm = term.trim().toLowerCase();

        return storageService.getAllSearchables().stream().filter(searchable -> searchable.getSearchTerm().contains(searchTerm)).collect(Collectors.toList());
    }
}
