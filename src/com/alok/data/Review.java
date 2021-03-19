package com.alok.data;

/**
 * The type Review.
 *
 * @author - Ravikant on 18/03/2021 - 10:53
 */
public class Review implements Comparable<Review> {
    private final Rating rating;
    private final String comments;

    /**
     * Instantiates a new Review.
     *
     * @param rating   the rating
     * @param comments the comments
     */
    public Review(Rating rating, String comments) {
        this.rating = rating;
        this.comments = comments;
    }

    /**
     * Gets rating.
     *
     * @return the rating
     */
    public Rating getRating() {
        return rating;
    }

    /**
     * Gets comments.
     *
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    @Override
    public String toString() {
        return "Review{" +
                "rating=" + rating +
                ", comments='" + comments + '\'' +
                '}';
    }

    @Override
    public int compareTo(Review other) {
        return other.rating.ordinal() - this.rating.ordinal();
    }
}
