package org.marco;

import org.marco.model.Client;
import org.marco.model.Product;
import org.marco.service.ClientService;
import org.marco.service.ProductService;
import org.marco.service.SalesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

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

        Client client = new Client(
                1,
                "Maria",
                "Reillo",
                "maria@email.com",
                1,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        ProductService.newProduct(product);
        ClientService.newClient(client);

        SalesService.newSale(product, client, 1);

    }
}