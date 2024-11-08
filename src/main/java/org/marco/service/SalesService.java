package org.marco.service;

import org.marco.dao.ConnectionManager;
import org.marco.dao.IClientDao;
import org.marco.dao.IProductDao;
import org.marco.dao.ISalesDao;
import org.marco.dao.impl.ClientDaoJdbc;
import org.marco.dao.impl.ProductDaoJdbc;
import org.marco.dao.impl.SalesDaoJdbc;
import org.marco.model.Client;
import org.marco.model.Product;
import org.marco.model.Sales;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class SalesService {
    private static final IProductDao productDao;
    private static final IClientDao clientDao;
    private static final ISalesDao salesDao;

    static {
        try {
            Connection conn = ConnectionManager.getInstance().getConnection();
            productDao = new ProductDaoJdbc(conn);
            clientDao = new ClientDaoJdbc(conn);
            salesDao = new SalesDaoJdbc(conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void newSale(Product product, Client client, int quantity) {
        Sales sales = new Sales(1, client, product, quantity, LocalDateTime.now());
        salesDao.insert(sales);
    }

    public static Product getMostPurchasedProduct() {
        return null;
    }

    public static Client getTopPurchasingClient() {
        return null;
    }

}
