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

    public static Client newClient(Client client) {

        if (clientDao.insert(client) != 0)
            return client;

        return null;
    }

    public static Client updateClient(Client newInfo) {
        if (clientDao.update(newInfo))
            return newInfo;

        return null;
    }

    public static boolean deleteClient(Client client) {
        return clientDao.delete(client.getId());
    }

    public static List<Client> getAllClients() {
        return clientDao.getAll();
    }

    public static Client getClientByEmail(String email) {
        return clientDao.getByEmail(email);
    }

}
