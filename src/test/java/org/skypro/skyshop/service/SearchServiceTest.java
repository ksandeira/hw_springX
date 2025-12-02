package org.skypro.skyshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.model.search.Searchable;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    @Mock
    private StorageService storageService;

    @InjectMocks
    private SearchService searchService;

    @Test
    void search_whenStorageServiceIsEmpty_shouldReturnEmptyList() {
        when(storageService.getAllSearchables()).thenReturn(Collections.emptyList());

        List<Searchable> result = searchService.search("test");

        assertNotNull(result);

        assertTrue(result.isEmpty());

        verify(storageService, times(1)).getAllSearchables();

    }

    @Test
    void search_whenObjectsExistButNoMatch_shouldReturnEmptyList() {

        Searchable testObject = new Searchable() {
            @Override
            public UUID getId() {
                return UUID.randomUUID();
            }

            @Override
            public String getSearchTerm() {
                return "собака";
            }

            @Override
            public String getContentType() {
                return "TEST";
            }
        };

        when(storageService.getAllSearchables()).thenReturn(List.of(testObject));

        List<Searchable> result = searchService.search("кошка");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(storageService, times(1)).getAllSearchables();

    }

    @Test
    void search_whenObjectMatches_shouldReturnObject() {
        Searchable matchingObject = new Searchable() {
            @Override
            public UUID getId() {
                return UUID.randomUUID();
            }

            @Override
            public String getSearchTerm() {
                return "корм для собак";
            }

            @Override
            public String getContentType() {
                return "PRODUCT";
            }
        };

        when(storageService.getAllSearchables()).thenReturn(List.of(matchingObject));

        List<Searchable> result = searchService.search("собак");

        assertNotNull(result);
        assertEquals(1,result.size());
        assertEquals(matchingObject, result.get(0));
        verify(storageService, times(1)).getAllSearchables();
    }

    @Test
    void search_whenTermIsEmpty_shouldReturnEmptyList() {
        List<Searchable> result = searchService.search("");

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(storageService, never()).getAllSearchables();
    }

    @Test
    void search_whenTermIsNull_shouldReturnEmptyList() {
        List<Searchable> result = searchService.search(null);
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(storageService, never()).getAllSearchables();
    }
}