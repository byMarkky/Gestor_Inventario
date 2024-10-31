package org.marco;

import org.marco.dao.ConnectionManager;
import org.marco.model.Product;
import org.marco.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws SQLException {

        Connection connection = ConnectionManager.getInstance().getConnection();

        Product product = new Product(
                1,
                "IPhone",
                "Iphone 5 Red",
                30,
                1200,
                true,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        ProductService.newProduct(product);
        Product productReceived = ProductService.getById(product.getId());
        logger.info("PRODUCT RECEIVED: {}", productReceived);
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (ProductService.deleteProduct(product)) {
            logger.info("PRODUCT DELETED SUCCESSFULLY");
        }

    }
}