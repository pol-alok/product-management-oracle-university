package com.alok.utils;

import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author - Ravikant on 20/03/2021 - 11:58
 * @package com.alok.utils
 * @project product-management-oracle-university
 */
public class Converter {
    public static void main(String[] args) {
        final String src = "{0}, 價格: {1}, 評分: {2}, 最好在...前: {3}\n審查: {0}\\t{1}\n未審查";
        final CharsetEncoder asciiEncoder = StandardCharsets.US_ASCII.newEncoder();
        final StringBuilder result = new StringBuilder();
        for (final Character character : src.toCharArray()) {
            if (asciiEncoder.canEncode(character)) {
                result.append(character);
            } else {
                result.append("\\u").append(Integer.toHexString(0x10000 | character).substring(1).toUpperCase());
            }
        }
        System.out.println(result);
    }
}
