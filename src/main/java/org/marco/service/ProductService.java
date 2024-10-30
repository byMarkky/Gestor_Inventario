package org.marco.service;

import org.marco.dao.ConnectionManager;
import org.marco.dao.impl.ProductDaoJdbc;
import org.marco.model.Product;

import java.sql.SQLException;
import java.util.List;

public class ProductService {
    private static ProductDaoJdbc productDAO;

    public static Product newProduct(Product product) {
        if (productDAO == null) {
            try {
                productDAO = new ProductDaoJdbc(ConnectionManager.getInstance().getConnection());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        productDAO.insert(product);

        return product;
    }

    public static Product updateProduct(Product newInfo) {
        return null;
    }

    public static boolean deleteProduct(Product prod) {
        return false;
    }

    public static Product getById(int productId) {
        return null;
    }

    public static List<Product> getAllProduct() {
        return null;
    }

    public static List<Product> getProductsByNameALike(Product name) {
        return null;
    }
}
