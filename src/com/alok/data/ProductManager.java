package com.alok.data;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * The type Product manager.
 *
 * @author - Ravikant on 16/03/2021 - 23:30
 */
public class ProductManager {

    private static final Logger logger = Logger.getLogger(ProductManager.class.getName());

    private ResourceFormatter formatter;

    private ResourceBundle config = ResourceBundle.getBundle("com.alok.data.config");

    private MessageFormat reviewFormat = new MessageFormat(config.getString("review.data.format"));
    private MessageFormat productFormat = new MessageFormat(config.getString("product.data.format"));

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
     * @throws ProductManagerException the product manager exception
     */
    public Product findProduct(int id) throws ProductManagerException {
        return products.keySet()
                .stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new ProductManagerException("Product with given id: " + id + " not found."));

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
        try {
            return reviewProduct(findProduct(id), rating, comments);
        } catch (ProductManagerException e) {
            logger.log(Level.INFO, e.getMessage());
        }
        return null;
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
        try {
            printProductReport(findProduct(id));
        } catch (ProductManagerException e) {
            logger.log(Level.INFO, e.getMessage());
        }
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
     * Parse review.
     *
     * @param text the text
     */
    public void parseReview(String text) {
        try {
            Object[] values = reviewFormat.parse(text);
            reviewProduct(Integer.parseInt((String) values[0]), Rateable.convert(Integer.parseInt((String) values[1])), (String) values[2]);
        } catch (ParseException | NumberFormatException e) {
            logger.log(Level.WARNING, "Error Parsing Review: " + text, e.getMessage());
        }
    }

    /**
     * Parse product.
     *
     * @param text the text
     */
    public void parseProduct(String text) {
        try {
            Object[] values = productFormat.parse(text);
            int id = Integer.parseInt((String) values[1]);
            String name = (String) values[2];
            BigDecimal price = BigDecimal.valueOf(Double.parseDouble((String) values[3]));
            Rating rating = Rateable.convert(Integer.parseInt((String) values[4]));
            switch ((String) values[0]) {
                case "D":
                    createProduct(id, name, price, rating);
                    break;
                case "F":
                    LocalDate bestBefore = LocalDate.parse((String) values[5]);
                    createProduct(id, name, price, rating, bestBefore);
            }
        } catch (ParseException | NumberFormatException | DateTimeParseException e) {
            logger.log(Level.WARNING, "Error Parsing Product: " + text, e.getMessage());
        }
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
