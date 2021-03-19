package com.alok.data;

import java.math.BigDecimal;
import java.time.LocalTime;

/**
 * The type Drink.
 *
 * @author - Ravikant on 16/03/2021 - 22:20
 */
public final class Drink extends Product {

    /**
     * Instantiates a new Drink.
     *
     * @param id     the id
     * @param name   the name
     * @param price  the price
     * @param rating the rating
     */
    Drink(int id, String name, BigDecimal price, Rating rating) {
        super(id, name, price, rating);
    }

    /**
     * Apply rating product.
     *
     * @param newRating the new rating
     * @return the product
     */
    @Override
    public Product applyRating(Rating newRating) {
        return new Drink(getId(), getName(), getPrice(), newRating);
    }

    /**
     * Gets discount.
     *
     * @return the discount
     */
    @Override
    public BigDecimal getDiscount() {
        LocalTime now = LocalTime.now();
        return (now.isAfter(LocalTime.of(17, 30)) && now.isBefore(LocalTime.of(18, 30))) ? super.getDiscount() : BigDecimal.ZERO;
    }
}
