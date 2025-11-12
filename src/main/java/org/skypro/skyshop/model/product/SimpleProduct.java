package org.skypro.skyshop.model.product;

import java.util.UUID;

public class SimpleProduct extends Product {
    public SimpleProduct(UUID id, String name, int cost) {
        super(id, name, cost);
    }
}
