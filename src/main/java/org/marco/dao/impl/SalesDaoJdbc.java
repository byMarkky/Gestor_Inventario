package org.marco.dao.impl;

import org.marco.dao.ISalesDao;
import org.marco.model.Client;
import org.marco.model.Product;
import org.marco.model.Sales;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class SalesDaoJdbc implements ISalesDao {

    private final Connection connection;

    public SalesDaoJdbc(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Sales toCreate) {
        String query = "INSERT INTO SALES(SALES_ID,PRODUCT_ID,CLIENT_ID,QUANTITY,DATE_OF_SALE) VALUES (?,?,?,?,?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, toCreate.getSalesId());
            statement.setInt(2, toCreate.getProduct().getId());
            statement.setInt(3, toCreate.getCustomer().getId());
            statement.setInt(4, toCreate.getQuantity());
            statement.setObject(5, LocalDateTime.now());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Product getMostPurchased() {
        return null;
    }

    @Override
    public Client getTopPurchasingClient() {
        return null;
    }
}
