package com.ocado.basket.model;

import java.util.List;

public class Supplier {
    private final String name;
    private final List<String> products;

    public Supplier(String name, List<String> products) {
        this.name = name;
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public List<String> getProducts() {
        return products;
    }

    public void deleteProduct(String productName) {
        products.remove(productName);
    }

}
