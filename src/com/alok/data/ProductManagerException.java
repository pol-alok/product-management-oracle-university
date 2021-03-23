package com.alok.data;

/**
 * @author - Ravikant on 22/03/2021 - 09:58
 * @package com.alok.data
 * @project product-management-oracle-university
 */
public class ProductManagerException extends Exception {
    public ProductManagerException() {
        super();
    }

    public ProductManagerException(String message) {
        super(message);
    }

    public ProductManagerException(String message, Throwable cause) {
        super(message, cause);
    }

}
