package com.alok.data;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * The type Product manager.
 *
 * @author - Ravikant on 16/03/2021 - 23:30
 */
public class ProductManager {

    private ResourceFormatter formatter;

    private final Map<Product, List<Review>> products = new HashMap<>();

    private static final Map<String, ResourceFormatter> formatters =
            new HashMap<String, ResourceFormatter>() {{
                put("en-US", new ResourceFormatter(Locale.US));
                put("fr-FR", new ResourceFormatter(Locale.FRANCE));
                put("ru-RU", new ResourceFormatter(new Locale("ru", "RU")));
                put("zh-TW", new ResourceFormatter(Locale.TRADITIONAL_CHINESE));
                put("hi-IN", new ResourceFormatter(new Locale("hi", "IN")));
            }};

    /**
     * Instantiates a new Product manager.
     *
     * @param locale the locale
     */
    public ProductManager(Locale locale) {
        this(locale.toLanguageTag());
    }

    /**
     * Instantiates a new Product manager.
     *
     * @param langTag the lang tag
     */
    public ProductManager(String langTag) {
        changeLocal(langTag);
    }

    /**
     * Change local.
     *
     * @param langTag the lang tag
     */
    public void changeLocal(String langTag) {
        formatter = formatters.getOrDefault(langTag, formatters.get("en-US"));
    }

    /**
     * Supported locals set.
     *
     * @return the set
     */
    public Set<String> supportedLocals() {
        return formatters.keySet();
    }

    /**
     * Create product product.
     *
     * @param id         the id
     * @param name       the name
     * @param price      the price
     * @param rating     the rating
     * @param bestBefore the best before
     * @return the product
     */
    public Product createProduct(int id, String name, BigDecimal price, Rating rating, LocalDate bestBefore) {
        Product product = new Food(id, name, price, rating, bestBefore);
        products.putIfAbsent(product, new ArrayList<>());
        return product;
    }

    /**
     * Create product product.
     *
     * @param id     the id
     * @param name   the name
     * @param price  the price
     * @param rating the rating
     * @return the product
     */
    public Product createProduct(int id, String name, BigDecimal price, Rating rating) {
        Product product = new Drink(id, name, price, rating);
        products.putIfAbsent(product, new ArrayList<>());
        return product;
    }

    /**
     * Find product product.
     *
     * @param id the id
     * @return the product
     */
    public Product findProduct(int id) {
        return products.keySet()
                .stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);

    }

    /**
     * Review product product.
     *
     * @param id       the id
     * @param rating   the rating
     * @param comments the comments
     * @return the product
     */
    public Product reviewProduct(int id, Rating rating, String comments) {
        return reviewProduct(findProduct(id), rating, comments);
    }

    /**
     * Review product product.
     *
     * @param product  the product
     * @param rating   the rating
     * @param comments the comments
     * @return the product
     */
    public Product reviewProduct(Product product, Rating rating, String comments) {
        List<Review> reviews = products.get(product);
        products.remove(product);
        reviews.add(new Review(rating, comments));

        product = product.applyRating(
                Rateable.convert(
                        (int) Math.round(
                                reviews.stream()
                                        .mapToInt(r -> r.getRating().ordinal())
                                        .average()
                                        .orElse(0)
                        )
                )
        );
        products.put(product, reviews);
        return product;
    }

    /**
     * Print product report.
     *
     * @param id the id
     */
    public void printProductReport(int id) {
        printProductReport(findProduct(id));
    }

    /**
     * Print product report.
     *
     * @param product the product
     */
    public void printProductReport(Product product) {
        List<Review> reviews = products.get(product);

        StringBuilder text = new StringBuilder();
        text.append(formatter.formatProduct(product));
        text.append("\n");
        Collections.sort(reviews);

        if (reviews.isEmpty()) {
            text.append(formatter.getText("no.review")).append('\n');
        } else {
            text.append(
                    reviews.stream()
                            .map(r -> formatter.formatReview(r) + '\n')
                            .collect(Collectors.joining())
            );
        }
        System.out.println(text);

    }

    /**
     * Print products.
     *
     * @param filter the filter
     * @param sorter the sorter
     */
    public void printProducts(Predicate<Product> filter, Comparator<Product> sorter) {
        StringBuilder text = new StringBuilder();
        text.append(
                products.keySet().stream()
                        .sorted(sorter)
                        .filter(filter)
                        .map(p -> formatter.formatProduct(p) + '\n')
                        .collect(Collectors.joining())
        );
        System.out.println(text);
    }

    /**
     * Gets discount.
     *
     * @return the discount
     */
    public Map<String, String> getDiscount() {
        return products.keySet()
                .stream()
                .collect(
                        Collectors.groupingBy(
                                product -> product.getRating().getStars(),
                                Collectors.collectingAndThen(
                                        Collectors.summingDouble(
                                                product -> product.getDiscount().doubleValue()
                                        ),
                                        discount -> formatter.moneyFormat.format(discount)
                                )
                        )
                );
    }



    private static class ResourceFormatter {
        private Locale locale;
        private ResourceBundle resources;
        private DateTimeFormatter dateFormat;
        private NumberFormat moneyFormat;

        private ResourceFormatter(Locale locale) {
            this.locale = locale;
            resources = ResourceBundle.getBundle("com.alok.data.resources", locale);
            dateFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(locale);
            moneyFormat = NumberFormat.getCurrencyInstance(locale);
        }

        private String formatProduct(Product product) {
            return MessageFormat.format(resources.getString("product"),
                    product.getName(),
                    moneyFormat.format(product.getPrice()),
                    product.getRating().getStars(),
                    dateFormat.format(product.getBestBefore()));
        }

        private String formatReview(Review review) {
            return MessageFormat.format(resources.getString("review"),
                    review.getRating().getStars(),
                    review.getComments());
        }

        private String getText(String key) {
            return resources.getString(key);
        }
    }
}
