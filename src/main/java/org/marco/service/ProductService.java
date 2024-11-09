package org.marco.service;

import org.marco.dao.ConnectionManager;
import org.marco.dao.IProductDao;
import org.marco.dao.impl.ProductDaoJdbc;
import org.marco.exceptions.CannotDeleteException;
import org.marco.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProductService {
    private static IProductDao productDAO;
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public static Product newProduct(Product product) {

        try (Connection conn = ConnectionManager.getInstance().getConnection()) {

            productDAO = new ProductDaoJdbc(conn);
            productDAO.insert(product);
            logger.info("PRODUCT CREATED, {}", product);

        } catch (SQLException e) {
            logger.error("PRODUCT CAN NOT BE CREATED, {}", e.getMessage());
            throw new RuntimeException(e);
        }

        return product;
    }

    public static Product updateProduct(Product newInfo) {

        Product res = null;

        try (Connection conn = ConnectionManager.getInstance().getConnection()) {

            productDAO = new ProductDaoJdbc(conn);

            newInfo.setUpdateDate(LocalDateTime.now());

            if (productDAO.update(newInfo)) {
                res = newInfo;
                logger.info("PRODUCT {} UPDATED", newInfo.getId());
            } else logger.info("PRODUCT DO NOT {} UPDATED", newInfo.getId());

        } catch (SQLException e) {
            logger.error("PRODUCT {} CAN NOT BE UPDATED", newInfo.getId());
            throw new RuntimeException(e);
        }

        return res;
    }

    public static boolean deleteProduct(Product prod) {

        boolean res;
        try (Connection conn = ConnectionManager.getInstance().getConnection()) {

            productDAO = new ProductDaoJdbc(conn);
            res = productDAO.delete(prod.getId());

            if (res) {
                logger.info("PRODUCT DELETED, {}", prod);
            } else {
                logger.info("PRODUCT NOT DELETED, {}", prod);
            }

        } catch (SQLException e) {
            logger.error("PRODUCT CAN NOT BE DELETED, {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return res;
    }

    public static Product getById(int productId) {
        Product res;

        try (Connection conn = ConnectionManager.getInstance().getConnection()) {

            productDAO = new ProductDaoJdbc(conn);
            res = productDAO.getById(productId);

            logger.info("PRODUCT GET BY ID: {}, PRODUCT: {}", productId, res);

        } catch (SQLException e) {
            logger.error("CAN'T GET THE PRODUCT WITH ID: {}, {}", productId, e.getMessage());
            throw new RuntimeException(e);
        }

        return res;
    }

    public static List<Product> getAllProduct() {

        List<Product> result = new ArrayList<>();

        try (Connection conn = ConnectionManager.getInstance().getConnection()) {
            productDAO = new ProductDaoJdbc(conn);
            result = productDAO.getAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public static List<Product> getProductsByNameALike(Product name) {
        List<Product> result = new ArrayList<>();

        try (Connection conn = ConnectionManager.getInstance().getConnection()) {
            productDAO = new ProductDaoJdbc(conn);
            result = productDAO.getAllByNameAlike(name.getName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
