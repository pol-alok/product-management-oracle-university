package com.alok.data;

/**
 * @author - Ravikant on 18/03/2021 - 09:55
 * @project product-management-oracle-university
 */
@FunctionalInterface
public interface Rateable<T> {

    public static final Rating DEFAULT_RATING = Rating.NOT_RATED;

    T applyRating(Rating rating);

    public default T applyRating(int stars) {
        return applyRating(convert(stars));
    }
    public default Rating getRating() {
        return DEFAULT_RATING;
    }

    public static Rating convert(int stars) {
        return (stars>=0 && stars<=5) ? Rating.values()[stars] : DEFAULT_RATING;
    }
}
