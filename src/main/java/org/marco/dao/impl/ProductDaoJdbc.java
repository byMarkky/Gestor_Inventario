package org.marco.dao.impl;

import org.marco.dao.IProductDao;
import org.marco.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ProductDaoJdbc implements IProductDao {

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
        return false;
    }

    @Override
    public boolean delete(int toDelete) {
        return false;
    }

    @Override
    public Product getById(int productId) {
        return null;
    }

    @Override
    public List<Product> getAll() {
        return List.of();
    }

    @Override
    public List<Product> getAllByNameAlike(String name) {
        return List.of();
    }

    @Override
    public boolean substractStock(int idToSubstract) {
        return false;
    }
}
