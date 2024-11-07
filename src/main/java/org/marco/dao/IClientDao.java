package org.marco.dao;

import org.marco.model.Client;

import java.util.List;

public interface IClientDao {
    /**
     * Insert new client in the database, if not exists
     * @param toCreate Client object to be created
     * @return Number of rows affected, n > 1 if the client has been added, else 0.
     */
    int insert(Client toCreate);

    /**
     * Method to update the data from a client, except the ID
     * @param toModify Client object to be updated
     * @return True if the client has been updated, false if not.
     */
    boolean update(Client toModify);

    /**
     * Method to delete a client from the database
     * @param idToDelete ID of the client to be deleted
     * @return True if has been deleted successfully, else false.
     */
    boolean delete(int idToDelete);

    /**
     * Method to increment the purchases of a client
     * @param clientId ID of the client
     * @param amount Amount of new purchases of the client
     * @return True if increment has been successful, else false.
     */
    boolean incrementPurchases(int clientId, int amount);

    /**
     * Method to get a client by his ID
     * @param clientId ID of the client we want
     * @return The client with the ID, if not exists the client returns null
     */
    Client getById(int clientId);

    /**
     * Method to get all the clients in the database
     * @return All the existing clients
     */
    List<Client> getAll();

    /**
     * Method to get a client object by his email.
     * @param email Email of the client we want
     * @return Client if exists, null if not.
     */
    Client getByEmail(String email);
}
