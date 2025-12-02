package org.skypro.skyshop.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.skyshop.exception.NoSuchProductException;
import org.skypro.skyshop.model.basket.BasketItem;
import org.skypro.skyshop.model.basket.ProductBasket;
import org.skypro.skyshop.model.basket.UserBasket;
import org.skypro.skyshop.model.product.SimpleProduct;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BasketServiceTest {

    @Mock
    private ProductBasket productBasket;

    @Mock
    private StorageService storageService;

    @InjectMocks
    private BasketService basketService;

    private final UUID existingProductId = UUID.randomUUID();
    private final UUID nonExistingProductId = UUID.randomUUID();
    private final SimpleProduct existingProduct = new SimpleProduct(existingProductId, "Корм для собак", 4000);

    @Test
    void addProduct_whenProductDoesNotExist_shouldThrowNoSuchProductException() {
        when(storageService.getProductById(nonExistingProductId)).thenReturn(Optional.empty());

        NoSuchProductException exception = assertThrows(NoSuchProductException.class, () -> basketService.addProduct(nonExistingProductId));

        assertEquals("Product not found with id: " + nonExistingProductId, exception.getMessage());

        verify(storageService, times(1)).getProductById(nonExistingProductId);
        verify(productBasket, never()).addProduct(any());
    }

    @Test
    void addProduct_whenProductExist_shouldCallProductBasketAddProduct() {
        when(storageService.getProductById(existingProductId)).thenReturn(Optional.of(existingProduct));

        basketService.addProduct(existingProductId);

        verify(storageService, times(1)).getProductById(existingProductId);
        verify(productBasket, times(1)).addProduct(existingProductId);
    }

    @Test
    void getUserBasket_whenProductBasketIsEmpty_shouldReturnEmptyUserBasket() {
        when(productBasket.getProducts()).thenReturn(Collections.emptyMap());

        UserBasket result = basketService.getUserBasket();

        assertNotNull(result);
        assertTrue(result.getItems().isEmpty());
        assertEquals(0, result.getTotal());
        verify(productBasket, times(1)).getProducts();
        verify(storageService, never()).getProductById(any());
    }

    @Test
    void getUserBasket_whenProductBasketHasProducts_shouldReturnCorrectUserBasket() {
        Map<UUID, Integer> basketProducts = new HashMap<>();

        basketProducts.put(existingProductId, 2);

        when(productBasket.getProducts()).thenReturn(basketProducts);

        when(storageService.getProductById(existingProductId)).thenReturn(Optional.of(existingProduct));

        int expectedTotal = 8000;
        UserBasket result = basketService.getUserBasket();

        assertNotNull(result);
        assertEquals(1, result.getItems().size());
        assertEquals(expectedTotal, result.getTotal());

        BasketItem item = result.getItems().get(0);
        assertEquals(existingProduct, item.getProduct());
        assertEquals(2, item.getQuantity());

        verify(productBasket, times(1)).getProducts();
        verify(storageService, times(1)).getProductById(existingProductId);
    }

    @Test
    void getUserBasket_whenProductInBasketNoLongerExists_shouldThrowException() {
        Map<UUID, Integer> basketProducts = new HashMap<>();
        basketProducts.put(nonExistingProductId, 1);

        when(productBasket.getProducts()).thenReturn(basketProducts);
        when(storageService.getProductById(nonExistingProductId)).thenReturn(Optional.empty());

        NoSuchProductException exception = assertThrows(
                NoSuchProductException.class,
                () -> basketService.getUserBasket()
        );

        assertEquals("Product not found: " + nonExistingProductId, exception.getMessage());

        verify(productBasket, times(1)).getProducts();
        verify(storageService, times(1)).getProductById(nonExistingProductId);
    }
}