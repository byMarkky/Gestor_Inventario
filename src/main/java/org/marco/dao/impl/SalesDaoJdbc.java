package org.marco.dao.impl;

import org.marco.dao.ISalesDao;
import org.marco.model.Client;
import org.marco.model.Product;
import org.marco.model.Sales;
import org.marco.service.ProductService;

import java.sql.*;
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

            connection.setAutoCommit(false);

            statement.setInt(1, toCreate.getSalesId());
            statement.setInt(2, toCreate.getProduct().getId());
            statement.setInt(3, toCreate.getCustomer().getId());
            statement.setInt(4, toCreate.getQuantity());
            statement.setObject(5, LocalDateTime.now());

            statement.executeUpdate();

            connection.commit();

            reduceStock(toCreate.getProduct());

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    private void reduceStock(Product product) {
        Product newProduct = new Product(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getStock() - 1,
                product.getPrice(),
                product.isAvailable(),
                product.getCreateDate(),
                product.getUpdateDate()
        );
        ProductService.updateProduct(newProduct);
    }

    @Override
    public Product getMostPurchased() {
        String procedureCall = "{ CALL getMostPurchasedProduct() }";

        Product product = null;

        try (CallableStatement callableStatement = connection.prepareCall(procedureCall)) {

            // Ejecutar el procedimiento
            ResultSet res = callableStatement.executeQuery();

            // Procesar el resultado
            if (res.next()) {
                product = new Product(
                        res.getInt("ID"),
                        res.getString("NAME"),
                        res.getString("DESCRIPTION"),
                        res.getInt("STOCK"),
                        res.getDouble("PRICE"),
                        res.getBoolean("AVAILABLE"),
                        (LocalDateTime) res.getObject("CREATE_DATE"),
                        (LocalDateTime) res.getObject("UPDATE_DATE")
                );

            } else {
                System.out.println("No se encontraron datos para el cliente con más compras.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public Client getTopPurchasingClient() {
        String procedureCall = "{ CALL getTopPurchasingClient() }";

        Client client = null;

        try (CallableStatement callableStatement = connection.prepareCall(procedureCall)) {

            // Ejecutar el procedimiento
            ResultSet res = callableStatement.executeQuery();

            // Procesar el resultado
            if (res.next()) {
                client = new Client(
                        res.getInt("ID"),
                        res.getString("NAME"),
                        res.getString("SURNAME"),
                        res.getString("EMAIL"),
                        res.getInt("PURCHASES"),
                        (LocalDateTime) res.getObject("CREATE_DATE"),
                        (LocalDateTime) res.getObject("UPDATE_DATE")
                );

            } else {
                System.out.println("No se encontraron datos para el cliente con más compras.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }
}
