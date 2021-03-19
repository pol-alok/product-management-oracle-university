package com.alok.data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import static com.alok.data.Rating.NOT_RATED;
import static java.math.RoundingMode.HALF_UP;

/**
 * The type Product.
 *
 * @author - Ravikant on 16/03/2021 - 12:13
 */
public abstract class Product implements Rateable<Product> {

    /**
     * The constant DISCOUNT_RATE.
     */
    public static final BigDecimal DISCOUNT_RATE = BigDecimal.valueOf(0.1);
    private final int id;
    private final String name;
    private final BigDecimal price;
    private final Rating rating;

    /**
     * Instantiates a new Product.
     *
     * @param id     the id
     * @param name   the name
     * @param price  the price
     * @param rating the rating
     */
    Product(int id, String name, BigDecimal price, Rating rating) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.rating = rating;
    }

    /**
     * Instantiates a new Product.
     *
     * @param id    the id
     * @param name  the name
     * @param price the price
     */
    Product(int id, String name, BigDecimal price) {
        this(id, name, price, NOT_RATED);
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * Gets discount.
     *
     * @return the discount
     */
    public BigDecimal getDiscount() {
        return price.multiply(DISCOUNT_RATE).setScale(2, HALF_UP);
    }

    /**
     * Gets rating.
     *
     * @return the rating
     */
    @Override
    public Rating getRating() {
        return rating;
    }

    /**
     * Gets best before.
     *
     * @return the best before
     */
    public LocalDate getBestBefore() {
        return LocalDate.now();
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return id + ", " + name + ", " + price + ", " + getDiscount() + ", " + getRating().getStars() + ", " + getBestBefore();
    }


    /**
     * Equals boolean.
     *
     * @param o the o
     * @return the boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Product) {
            final Product other = (Product) o;
            return this.id == other.id;
        }
        return false;
    }


    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
