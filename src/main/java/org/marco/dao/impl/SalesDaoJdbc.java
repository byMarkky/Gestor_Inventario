package org.marco.dao.impl;

import org.marco.dao.ISalesDao;
import org.marco.exceptions.GeneralErrorException;
import org.marco.exceptions.InventoryException;
import org.marco.model.Client;
import org.marco.model.Product;
import org.marco.model.Sales;
import org.marco.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;

public class SalesDaoJdbc implements ISalesDao {

    private static final Logger logger = LoggerFactory.getLogger(SalesDaoJdbc.class);
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
            logger.info("SALES {} CREATED", toCreate.getSalesId());

        } catch (SQLException e) {
            try {
                logger.error("CALLBACK CREATING SALE {}", toCreate.getSalesId());
                connection.rollback();
            } catch (SQLException ex) {
                logger.error("UNABLE TO CREATE A SALE: {}", toCreate.getSalesId());
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    private void reduceStock(Product product) {
        int stock = 0;
        try {
            Statement statement = connection.createStatement();
            ResultSet res = statement.executeQuery("SELECT STOCK FROM PRODUCT WHERE ID=" + product.getId());
            if (res.next()) {
                stock = res.getInt("STOCK");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (stock - 1 < 0) throw new InventoryException("NEGATIVE STOCK AT PRODUCT " + product.getId());

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
        ProductService productService = new ProductService();
        productService.updateProduct(newProduct);
    }

    @Override
    public Product getMostPurchased() {
        String procedureCall = "{ CALL getMostPurchasedProduct() }";

        Product product = null;

        try (CallableStatement callableStatement = connection.prepareCall(procedureCall)) {

            ResultSet res = callableStatement.executeQuery();

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
                logger.error("CANT GET THE MOST PURCHASED PRODUCT");
                throw new GeneralErrorException("Do not found data for the product");
            }

        } catch (SQLException e) {
            logger.error("ERROR GETTING MOST PURCHASED PRODUCT: {}", e.getMessage());
            throw new GeneralErrorException("Error trying to get the most purchased product");
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
                logger.error("CANT GET THE MOST PURCHASING CLIENT");
                throw new GeneralErrorException("Do not found data for the client");
            }

        } catch (SQLException e) {
            logger.error("ERROR GETTING MOST PURCHASING CLIENT: {}", e.getMessage());
            throw new GeneralErrorException("Error trying to get the most purchasing client");
        }
        return client;
    }
}
