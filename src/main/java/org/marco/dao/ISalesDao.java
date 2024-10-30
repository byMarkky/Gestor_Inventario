package org.marco.dao;

import org.marco.model.Client;
import org.marco.model.Product;
import org.marco.model.Sales;

public interface ISalesDao {
    void insert(Sales toCreate);
    Product getMostPurchased();
    Client getTopPurchasingClient();
}
