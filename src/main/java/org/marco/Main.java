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

    public static void main(String[] args) throws InterruptedException {

        ClientService clientService = new ClientService();
        ProductService productService = new ProductService();
        SalesService salesService = new SalesService();

        Client client = new Client(
                2,
                "Samuel",
                "Deluque",
                "samuel@email.com",
                20,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        clientService.deleteClient(client);

        clientService.newClient(client);

        Thread.sleep(5_000);

        client.setName("Mariano");
        clientService.updateClient(client);

        Product product = new Product(
                3,
                "Logitech G29",
                "Volante logitech",
                1,
                300,
                true,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        productService.deleteProduct(product);
        productService.newProduct(product);

        salesService.newSale(product, client, 1);

        Client most = salesService.getTopPurchasingClient();

        System.out.println(most);

    }
}