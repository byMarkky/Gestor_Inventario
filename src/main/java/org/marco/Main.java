package org.marco;

import org.marco.model.Client;
import org.marco.model.Product;
import org.marco.service.ClientService;
import org.marco.service.ProductService;
import org.marco.service.SalesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {


        Product product = new Product(
                1,
                "MacBook Air 13",
                "MacBook Air 13\" 8GB RAM, 520GB",
                14,
                1500,
                true,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        ProductService.deleteProduct(product);

        ProductService.newProduct(product);

        product.setAvailable(false);

        Thread.sleep(10_000);

        ProductService.updateProduct(product);

        List<Product> product1 = ProductService.getProductsByNameALike(product);

    }
}