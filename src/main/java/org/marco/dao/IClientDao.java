package org.marco.dao;

import org.marco.model.Client;

import java.util.List;

public interface IClientDao {
    int insert(Client toCreate);
    boolean update(Client toModify);
    boolean delete(int idToDelete);
    int incrementPurchases(int amount);
    Client getById(int clientId);
    List<Client> getAll();
    Client getByEmail(String email);
}
