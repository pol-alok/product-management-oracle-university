package com.alok.data;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

/**
 * The type Product manager.
 *
 * @author - Ravikant on 16/03/2021 - 23:30
 * @project product-management-oracle-university
 */
public class ProductManager {

    private Locale locale;
    private ResourceBundle resources;
    private DateTimeFormatter dateFormat;
    private NumberFormat moneyFormat;


    //    private Product product;
//    private Review[] reviews = new Review[5];
    private final Map<Product, List<Review>> products = new HashMap<>();

    public ProductManager(Locale locale) {
        this.locale = locale;
        resources = ResourceBundle.getBundle("com.alok.data.resources", locale);
        dateFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(locale);
        moneyFormat = NumberFormat.getCurrencyInstance(locale);
    }

    public Product createProduct(int id, String name, BigDecimal price, Rating rating, LocalDate bestBefore) {
        Product product = new Food(id, name, price, rating, bestBefore);
        products.putIfAbsent(product, new ArrayList<>());
        return product;
    }

    public Product createProduct(int id, String name, BigDecimal price, Rating rating) {
        Product product = new Drink(id, name, price, rating);
        products.putIfAbsent(product, new ArrayList<>());
        return product;
    }

    public Product findProduct(int id) {
        for (Product product : products.keySet())
            if (product.getId() == id)
                return product;
        return null;
    }

    public Product reviewProduct(int id, Rating rating, String comments) {
        return reviewProduct(findProduct(id),rating,comments);
    }

    public Product reviewProduct(Product product, Rating rating, String comments) {
        List<Review> reviews = products.get(product);
        products.remove(product);
        reviews.add(new Review(rating, comments));
        int sum = 0;
        for (Review review : reviews) {
            sum += review.getRating().ordinal();
        }
        product = product.applyRating(Rateable.convert(Math.round((float) sum / reviews.size())));
        products.put(product, reviews);
        return product;
    }

    public void printProductReport(int id) {
        printProductReport(findProduct(id));
    }

    public void printProductReport(Product product) {
        List<Review> reviews = products.get(product);

        StringBuilder text = new StringBuilder();
        text.append(MessageFormat.format(resources.getString("product"),
                product.getName(),
                moneyFormat.format(product.getPrice()),
                product.getRating().getStars(),
                dateFormat.format(product.getBestBefore())));
        text.append("\n");
        Collections.sort(reviews);
        for (Review review : reviews) {
            text.append(MessageFormat.format(resources.getString("review"),
                    review.getRating().getStars(),
                    review.getComments()));
            text.append("\n");
        }
        if (reviews.isEmpty()) {
            text.append(resources.getString("no.review"));
            text.append("\n");
        }
        System.out.println(text);

    }


}
