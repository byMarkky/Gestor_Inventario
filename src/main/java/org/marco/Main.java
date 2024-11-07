package org.marco;

import org.marco.dao.ConnectionManager;
import org.marco.model.Client;
import org.marco.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        Client client = new Client(
                2,
                "Maria",
                "Reillo",
                "maria.reillo@email.com",
                3,
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        ClientService.newClient(client);
        logger.info("CLIENT MARIA CREATED");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        client.setPurchases(14);
        client.setEmail("marreillo@email.org");
        ClientService.updateClient(client);
        logger.info("CLIENT MARIA UPDATED");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        ClientService.deleteClient(client);
        logger.info("CLIENT MARIA DELETED");

        Client client1 = ClientService.getClientByEmail("marceo@email.com");
        logger.info("CLIENT BY EMAIL: {}", client1);

    }
}