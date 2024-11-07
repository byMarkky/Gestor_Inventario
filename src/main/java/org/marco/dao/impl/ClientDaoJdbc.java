package org.marco.dao.impl;

import org.marco.dao.IClientDao;
import org.marco.exceptions.CannotDeleteException;
import org.marco.model.Client;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClientDaoJdbc implements IClientDao {

    private final Connection connection;

    public ClientDaoJdbc(Connection conn) {
        this.connection = conn;
    }

    @Override
    public int insert(Client toCreate) {
        int result;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO " +
                            "CLIENT(ID,NAME,SURNAME,EMAIL,PURCHASES,CREATE_DATE,UPDATE_DATE) " +
                            "VALUES(?,?,?,?,?,?,?)"
            );

            statement.setInt(1, toCreate.getId());
            statement.setString(2, toCreate.getName());
            statement.setString(3, toCreate.getSurname());
            statement.setString(4, toCreate.getEmail());
            statement.setDouble(5, toCreate.getPurchases());
            statement.setObject(6, toCreate.getCreateDate());
            statement.setObject(7, toCreate.getUpdateDate());

            result = statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public boolean update(Client toModify) {
        String query = "UPDATE CLIENT SET ID=?,NAME=?,SURNAME=?,EMAIL=?,PURCHASES=?,UPDATE_DATE=? WHERE ID=?";
        boolean res = false;
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, toModify.getId());
            statement.setString(2, toModify.getName());
            statement.setString(3, toModify.getSurname());
            statement.setString(4, toModify.getEmail());
            statement.setInt(5, toModify.getPurchases());
            statement.setObject(6, LocalDateTime.now());

            statement.setInt(7, toModify.getId());  // WHERE ID = product.ID

            if (statement.executeUpdate() == 1) res = true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return res;
    }

    @Override
    public boolean delete(int idToDelete) {
        String query = "DELETE FROM CLIENT WHERE ID=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idToDelete);

            if (preparedStatement.executeUpdate() == 1) return true;

        } catch (SQLException e) {
            throw new CannotDeleteException(e.toString());
        }

        return false;
    }

    @Override
    public boolean incrementPurchases(int clientId, int amount) {
        String query = "UPDATE CLIENT SET PURCHASES=? WHERE ID=?";
        Client client = getById(clientId);

        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, client.getPurchases() + amount);
            statement.setInt(2, clientId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Client getById(int clientId) {
        String query = "SELECT * FROM CLIENT WHERE ID=?";
        Client client = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, clientId);
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()) {
                client = new Client(
                        res.getInt("ID"),
                        res.getString("NAME"),
                        res.getString("SURNAME"),
                        res.getString("EMAIL"),
                        res.getInt("PURCHASES"),
                        (LocalDateTime) res.getObject("CREATE_DATE"),
                        (LocalDateTime) res.getObject("UPDATE_DATE"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return client;
    }

    @Override
    public List<Client> getAll() {
        String query = "SELECT * FROM CLIENT";
        List<Client> result = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {

            ResultSet res = statement.executeQuery(query);

            while (res.next()) {
                result.add(new Client(
                        res.getInt("ID"),
                        res.getString("NAME"),
                        res.getString("SURNAME"),
                        res.getString("EMAIL"),
                        res.getInt("PURCHASES"),
                        (LocalDateTime) res.getObject("CREATE_DATE"),
                        (LocalDateTime) res.getObject("UPDATE_DATE"))
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public Client getByEmail(String email) {
        Client client = null;
        String query = "SELECT * FROM CLIENT WHERE EMAIL=?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet res = statement.executeQuery();

            while (res.next()) {
                client = new Client(
                        res.getInt("ID"),
                        res.getString("NAME"),
                        res.getString("SURNAME"),
                        res.getString("EMAIL"),
                        res.getInt("PURCHASES"),
                        (LocalDateTime) res.getObject("CREATE_DATE"),
                        (LocalDateTime) res.getObject("UPDATE_DATE")
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return client;
    }
}
