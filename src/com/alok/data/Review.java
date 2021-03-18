package com.alok.data;

import java.util.Random;

/**
 * @author - Ravikant on 18/03/2021 - 10:53
 * @project product-management-oracle-university
 */
public class Review implements Comparable<Review> {
    private final Rating rating;
    private final String comments;

    public Review(Rating rating, String comments) {
        this.rating = rating;
        this.comments = comments;
    }

    public Rating getRating() {
        return rating;
    }

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
