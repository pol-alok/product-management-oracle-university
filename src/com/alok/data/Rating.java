package com.alok.data;

/**
 * The enum Rating.
 *
 * @author - Ravikant on 16/03/2021 - 12:20
 *
 */
public enum Rating {

    /**
     * Not rated rating.
     */
    NOT_RATED("\u2606\u2606\u2606\u2606\u2606"),
    /**
     * One star rating.
     */
    ONE_STAR("\u2605\u2606\u2606\u2606\u2606"),
    /**
     * Two star rating.
     */
    TWO_STAR("\u2605\u2605\u2606\u2606\u2606"),
    /**
     * Three star rating.
     */
    THREE_STAR("\u2605\u2605\u2605\u2606\u2606"),
    /**
     * Four star rating.
     */
    FOUR_STAR("\u2605\u2605\u2605\u2605\u2606"),
    /**
     * Five star rating.
     */
    FIVE_STAR("\u2605\u2605\u2605\u2605\u2605");

    private final String stars;

    private Rating(String stars) {
        this.stars = stars;
    }

    /**
     * Gets stars.
     *
     * @return the stars
     */
    public String getStars() {
        return stars;
    }
}
