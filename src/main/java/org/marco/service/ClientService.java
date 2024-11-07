package org.marco.service;

import org.marco.dao.ConnectionManager;
import org.marco.dao.IClientDao;
import org.marco.dao.impl.ClientDaoJdbc;
import org.marco.model.Client;

import java.sql.SQLException;
import java.util.List;

public class ClientService {
    private static final IClientDao clientDao;

    static {
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
    public static Client newClient(Client client) {

        if (clientDao.insert(client) != 0)
            return client;

        return null;
    }

    /**
     * Method to update an existing client
     * @param newInfo Update client info.
     * @return The new client data
     */
    public static Client updateClient(Client newInfo) {
        if (clientDao.update(newInfo))
            return newInfo;

        return null;
    }

    /**
     * Method to delete a client from the database
     * @param client Client to be deleted
     * @return True if the client has been successfully remove, else, false.
     */
    public static boolean deleteClient(Client client) {
        return clientDao.delete(client.getId());
    }

    /**
     * Get all clients from the database
     * @return List of clients
     */
    public static List<Client> getAllClients() {
        return clientDao.getAll();
    }

    /**
     * Get a client by his email
     * @param email Email of the client we want
     * @return The client with the specified email
     */
    public static Client getClientByEmail(String email) {
        return clientDao.getByEmail(email);
    }

}
