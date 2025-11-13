package org.skypro.skyshop.model.product;

import java.util.UUID;

public class DiscountedProduct extends Product {
    private final int discountPercent;

    public DiscountedProduct(UUID id, String name, int cost, int discountPercent) {
        super(id, name, cost);
        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Скидка должна быть в диапазоне от 0 до 100%");
        }
        this.discountPercent = discountPercent;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    @Override
    public int getCost() {
        int discountAmount = super.getCost() * discountPercent / 100;
        return super.getCost() - discountAmount;
    }

    @Override
    public String getStringRepresentation() {
        return getName() + " - " + getCost() + " руб. (скидка " + discountPercent + "%)";
    }
}
