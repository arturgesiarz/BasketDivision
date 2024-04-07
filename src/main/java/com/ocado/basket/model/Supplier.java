package com.ocado.basket.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Supplier {
    private final String name;
    private final List<Product> products = new ArrayList<>();
    private int maxProducts = 0;

    public Supplier(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void deleteProduct(Product product) {
        products.remove(product);
    }

    public void addProduct(Product product) {
        if (!products.contains(product)) {
            products.add(product);
        }
        maxProducts = Math.max(maxProducts, products.size());
    }

    public int getMaxProducts() {
        return maxProducts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Supplier supplier = (Supplier) o;
        return name.equals(supplier.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
