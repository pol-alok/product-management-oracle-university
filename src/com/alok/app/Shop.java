package com.alok.app;

import com.alok.data.ProductManager;
import com.alok.data.Rating;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * The type Shop.
 *
 * @author - Ravikant on 16/03/2021 - 12:15
 */
public class Shop {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {

        ProductManager pm = new ProductManager(Locale.TRADITIONAL_CHINESE);

        pm.createProduct(101, "Tea", BigDecimal.valueOf(0.99), Rating.NOT_RATED);
        pm.printProductReport(101);
//        pm.reviewProduct(101, Rating.FOUR_STAR, "Nice hot cup of tea.");
//        pm.reviewProduct(101, Rating.TWO_STAR, "Rather week tea");
//        pm.reviewProduct(101, Rating.FOUR_STAR, "Fine tea");
//        pm.reviewProduct(101, Rating.FOUR_STAR, "Good tea");
//        pm.reviewProduct(101, Rating.FIVE_STAR, "Perfect tea");
//        pm.reviewProduct(101, Rating.THREE_STAR, "Just add some lemon");
        pm.parseReview("101, x, Fine tea");
        pm.printProductReport(101);

//        pm.changeLocal("hi-IN");


//        pm.createProduct(102, "Coffee", BigDecimal.valueOf(1.99), Rating.NOT_RATED);
/*        pm.printProductReport(102);
        pm.reviewProduct(102, Rating.THREE_STAR, "Coffee was ok");
        pm.reviewProduct(102, Rating.ONE_STAR, "Where is the milk?!");
        pm.reviewProduct(102, Rating.FIVE_STAR, "It's perfect with ten spoon of sugar!");
        pm.printProductReport(102);

        Comparator<Product> byRating = (p1, p2) -> p2.getRating().ordinal() - p1.getRating().ordinal();
        Comparator<Product> byPrice = (p1, p2) -> p2.getPrice().compareTo(p1.getPrice());
        Predicate<Product> filterByRating = p -> p.getPrice().floatValue() < 2;

        pm.printProducts(filterByRating, byRating.thenComparing(byPrice).reversed());
        pm.getDiscount()
                .forEach((rating, discount) -> System.out.println(rating + "\t" + discount));*/

    }
}
