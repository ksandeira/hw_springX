package org.skypro.skyshop.model.product;

import java.util.UUID;

public class FixPriceProduct extends Product {
    private static final int FIXED_PRICE = 500;

    public FixPriceProduct(UUID id, String name) {
        super(id, name, FIXED_PRICE);
    }

    @Override
    public String getStringRepresentation() {
        return getName() + " - " + getCost() + " руб. (фикс. цена)";
    }
}
