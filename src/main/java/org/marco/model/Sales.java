package org.marco.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Sales {
    private int salesId;
    private Client customer;
    private Product product;
    private int quantity;
    private LocalDateTime dateOfSale;

    public Sales(int salesId, Client customer, Product product, int quantity, LocalDateTime dateOfSale) {
        this.salesId = salesId;
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
        this.dateOfSale = dateOfSale;
    }

    public int getSalesId() {
        return salesId;
    }

    public void setSalesId(int salesId) {
        this.salesId = salesId;
    }

    public Client getCustomer() {
        return customer;
    }

    public void setCustomer(Client customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(LocalDateTime dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sales sales = (Sales) o;
        return salesId == sales.salesId && quantity == sales.quantity && Objects.equals(customer, sales.customer) && Objects.equals(product, sales.product) && Objects.equals(dateOfSale, sales.dateOfSale);
    }

    @Override
    public int hashCode() {
        return Objects.hash(salesId, customer, product, quantity, dateOfSale);
    }

    @Override
    public String toString() {
        return "Sales{" +
                "salesId=" + salesId +
                ", customer=" + customer +
                ", product=" + product +
                ", quantity=" + quantity +
                ", dateOfSale=" + dateOfSale +
                '}';
    }
}
