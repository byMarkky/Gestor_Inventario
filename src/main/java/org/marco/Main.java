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

        Client client = new Client(
                2,
                "Samuel",
                "Deluque",
                "samuel@email.com",
                20,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        ClientService.deleteClient(client);

        ClientService.newClient(client);

        Thread.sleep(5_000);

        client.setName("Mariano");
        ClientService.updateClient(client);

    }
}