package com.alok.data;

/**
 * The interface Rateable.
 *
 * @param <T> the type parameter
 * @author - Ravikant on 18/03/2021 - 09:55
 */
@FunctionalInterface
public interface Rateable<T> {

    /**
     * The constant DEFAULT_RATING.
     */
    public static final Rating DEFAULT_RATING = Rating.NOT_RATED;

    /**
     * Apply rating t.
     *
     * @param rating the rating
     * @return the t
     */
    T applyRating(Rating rating);

    /**
     * Apply rating t.
     *
     * @param stars the stars
     * @return the t
     */
    public default T applyRating(int stars) {
        return applyRating(convert(stars));
    }

    /**
     * Gets rating.
     *
     * @return the rating
     */
    public default Rating getRating() {
        return DEFAULT_RATING;
    }

    /**
     * Convert rating.
     *
     * @param stars the stars
     * @return the rating
     */
    public static Rating convert(int stars) {
        return (stars>=0 && stars<=5) ? Rating.values()[stars] : DEFAULT_RATING;
    }
}
