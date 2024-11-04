package org.marco.dao;


import org.marco.model.Product;

import java.util.List;

public interface IProductDao {
    int insert(Product toCreate);
    boolean update(Product toModify);
    boolean delete(int toDelete);
    Product getById(int productId);
    List<Product> getAll();
    List<Product> getAllByNameAlike(String name);
    boolean substractStock(int idToSubstract, int amount);
}
