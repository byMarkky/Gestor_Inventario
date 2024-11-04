package org.marco;

import org.marco.dao.ConnectionManager;
import org.marco.model.Product;
import org.marco.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

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
        Product product2 = new Product(
                2,
                "Acer Swift",
                "Acer Swift 14 16GB RAM, 1TB SSD",
                14,
                799,
                true,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        Product toUpdate = new Product(
                1,
                "IPhone",
                "Iphone 16 Red",
                40,
                1400,
                true,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        ProductService.deleteProduct(product);
        ProductService.deleteProduct(product2);

        ProductService.newProduct(product);
        ProductService.newProduct(product2);
        try {
            Thread.sleep(10_000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Product updated = ProductService.updateProduct(toUpdate);
        if (updated != null) {
            logger.info("UPDATED PRODUCT: {}", updated);
        }

        List<Product> products = ProductService.getProductsByNameALike(product2);

        if (!products.isEmpty()) {
            for (Product p : products) {
                logger.info("{}", p);
            }
        }

    }
}