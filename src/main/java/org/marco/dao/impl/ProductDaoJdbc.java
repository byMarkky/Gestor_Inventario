package org.marco.dao.impl;

import org.marco.dao.IProductDao;
import org.marco.exceptions.CannotDeleteException;
import org.marco.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoJdbc implements IProductDao {

    private static final Logger logger = LoggerFactory.getLogger(ProductDaoJdbc.class);
    private final Connection connection;

    public ProductDaoJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int insert(Product toCreate) {
        int result = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO " +
                            "PRODUCT(ID,NAME,DESCRIPTION,STOCK,PRICE,AVAILABLE,CREATE_DATE,UPDATE_DATE) " +
                            "VALUES(?,?,?,?,?,?,?,?)"
            );

            statement.setInt(1, toCreate.getId());
            statement.setString(2, toCreate.getName());
            statement.setString(3, toCreate.getDescription());
            statement.setInt(4, toCreate.getStock());
            statement.setDouble(5, toCreate.getPrice());
            statement.setBoolean(6, toCreate.isAvailable());
            statement.setObject(7, toCreate.getCreateDate());
            statement.setObject(8, toCreate.getUpdateDate());

            result = statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public boolean update(Product toModify) {

        String query = "UPDATE PRODUCT SET ID=?,NAME=?,DESCRIPTION=?,STOCK=?,PRICE=?,AVAILABLE=?,UPDATE_DATE=? WHERE ID=?";
        boolean res = false;
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, toModify.getId());
            statement.setString(2, toModify.getName());
            statement.setString(3, toModify.getDescription());
            statement.setInt(4, toModify.getStock());
            statement.setDouble(5, toModify.getPrice());
            statement.setBoolean(6, toModify.isAvailable());
            statement.setObject(7, LocalDateTime.now());

            statement.setInt(8, toModify.getId());  // WHERE ID = product.ID

            if (statement.executeUpdate() == 1) res = true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return res;
    }

    @Override
    public boolean delete(int toDelete) {
        //if (!haveSales(toDelete)) return false;

        String query = "DELETE FROM PRODUCT WHERE ID=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, toDelete);

            if (preparedStatement.executeUpdate() == 1) return true;

        } catch (SQLException e) {
            throw new CannotDeleteException(e.toString());
        }

        return false;
    }

    @Override
    public Product getById(int productId) {
        String query = "SELECT * FROM PRODUCT WHERE ID=?";
        Product product = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, productId);
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()) {
                product = new Product(
                        res.getInt("ID"),
                        res.getString("NAME"),
                        res.getString("DESCRIPTION"),
                        res.getInt("STOCK"),
                        res.getDouble("PRICE"),
                        res.getBoolean("AVAILABLE"),
                        (LocalDateTime) res.getObject("CREATE_DATE"),
                        (LocalDateTime) res.getObject("UPDATE_DATE"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return product;
    }

    private boolean haveSales(int productId) {
        boolean haveSales = false;
        String query = "SELECT * FROM SALES S WHERE PRODUCT_ID=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, productId);
            ResultSet res = preparedStatement.executeQuery();

            if (res.next()) return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return haveSales;
    }

    @Override
    public List<Product> getAll() {
        String query = "SELECT * FROM PRODUCT";
        List<Product> result = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {

            ResultSet res = statement.executeQuery(query);

            while (res.next()) {
                result.add(new Product(
                        res.getInt("ID"),
                        res.getString("NAME"),
                        res.getString("DESCRIPTION"),
                        res.getInt("STOCK"),
                        res.getDouble("PRICE"),
                        res.getBoolean("AVAILABLE"),
                        (LocalDateTime) res.getObject("CREATE_DATE"),
                        (LocalDateTime) res.getObject("UPDATE_DATE")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public List<Product> getAllByNameAlike(String name) {
        String query = "SELECT * FROM PRODUCT WHERE NAME=?";
        List<Product> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, name);

            ResultSet res = statement.executeQuery();

            while (res.next()) {
                result.add(new Product(
                        res.getInt("ID"),
                        res.getString("NAME"),
                        res.getString("DESCRIPTION"),
                        res.getInt("STOCK"),
                        res.getDouble("PRICE"),
                        res.getBoolean("AVAILABLE"),
                        (LocalDateTime) res.getObject("CREATE_DATE"),
                        (LocalDateTime) res.getObject("UPDATE_DATE")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @Override
    public boolean substractStock(int idToSubstract, int amount) {
        String query = "UPDATE PRODUCT SET STOCK=? WHERE ID=?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, amount);
            statement.setInt(2, idToSubstract);

            if (statement.executeUpdate() != 0) return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}
