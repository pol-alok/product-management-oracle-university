package com.alok.data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * The type Food.
 *
 * @author - Ravikant on 16/03/2021 - 22:18
 * @project product-management-oracle-university
 */
public final class Food extends Product {
    private final LocalDate bestBefore;

    /**
     * Instantiates a new Food.
     *
     * @param id         the id
     * @param name       the name
     * @param price      the price
     * @param rating     the rating
     * @param bestBefore the best before
     */
    Food(int id, String name, BigDecimal price, Rating rating, LocalDate bestBefore) {
        super(id, name, price, rating);
        this.bestBefore = bestBefore;
    }

    /**
     * Gets best before.
     *
     * @return the best before
     */
    @Override
    public LocalDate getBestBefore() {
        return bestBefore;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return super.toString() + ", " + bestBefore;
    }

    /**
     * Gets discount.
     *
     * @return the discount
     */
    @Override
    public BigDecimal getDiscount() {
        return (bestBefore.equals(LocalDate.now())) ? super.getDiscount() : BigDecimal.ZERO;
    }

    /**
     * Apply rating product.
     *
     * @param newRating the new rating
     * @return the product
     */
    @Override
    public Product applyRating(Rating newRating) {
        return new Food(getId(), getName(), getPrice(), newRating, bestBefore);
    }
}
