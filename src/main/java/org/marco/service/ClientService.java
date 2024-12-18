package org.marco.service;

import org.marco.dao.ConnectionManager;
import org.marco.dao.IClientDao;
import org.marco.dao.impl.ClientDaoJdbc;
import org.marco.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class ClientService {
    private final IClientDao clientDao;
    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    {
        try {
            clientDao = new ClientDaoJdbc(ConnectionManager.getInstance().getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to create new client in the database
     * @param client Client to be created
     * @return The client created
     */
    public Client newClient(Client client) {

        if (clientDao.insert(client) != 0)
            return client;

        return null;
    }

    /**
     * Method to update an existing client
     * @param newInfo Update client info.
     * @return The new client data
     */
    public Client updateClient(Client newInfo) {

        newInfo.setUpdateDate(LocalDateTime.now());

        if (clientDao.update(newInfo)) {
            logger.info("CLIENT {} UPDATED", newInfo.getId());
            return newInfo;
        }

        logger.info("CLIENT {} DO NOT UPDATED", newInfo.getId());
        return null;
    }

    /**
     * Method to delete a client from the database
     * @param client Client to be deleted
     * @return True if the client has been successfully remove, else, false.
     */
    public boolean deleteClient(Client client) {
        return clientDao.delete(client.getId());
    }

    /**
     * Get all clients from the database
     * @return List of clients
     */
    public List<Client> getAllClients() {
        return clientDao.getAll();
    }

    /**
     * Get a client by his email
     * @param email Email of the client we want
     * @return The client with the specified email
     */
    public Client getClientByEmail(String email) {
        return clientDao.getByEmail(email);
    }

}
