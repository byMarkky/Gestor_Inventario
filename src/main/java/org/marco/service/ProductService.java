package org.marco.service;

import org.marco.dao.ConnectionManager;
import org.marco.dao.IProductDao;
import org.marco.dao.impl.ProductDaoJdbc;
import org.marco.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ProductService {
    private static IProductDao productDAO;
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public static Product newProduct(Product product) {

        try (Connection conn = ConnectionManager.getInstance().getConnection()) {

            productDAO = new ProductDaoJdbc(conn);
            productDAO.insert(product);
            logger.info("PRODUCT INSERTED, {}", product);

        } catch (SQLException e) {
            logger.error("PRODUCT CAN NOT BE INSERTED, {}", e.getMessage());
            throw new RuntimeException(e);
        }

        return product;
    }

    public static Product updateProduct(Product newInfo) {
        return null;
    }

    public static boolean deleteProduct(Product prod) {
        boolean res = false;
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
        return null;
    }

    public static List<Product> getProductsByNameALike(Product name) {
        return null;
    }
}
