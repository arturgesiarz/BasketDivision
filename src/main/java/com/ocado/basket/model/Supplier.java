package com.ocado.basket.model;

import java.util.*;

public class Supplier {
    private final String name;
    private final List<Product> products = new ArrayList<>();
    private final Map<String, Integer> productsMap = new HashMap<>();
    private int maxProducts = 0;
    private int actProducts = 0;

    public Supplier(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public int getActProductsNumber() {
        return actProducts;
    }

    public Optional<Integer> getProductIndex(Product product) {
        return Optional.ofNullable(productsMap.get(product.name()));
    }

    public void deleteProduct(Product product) {
        Optional<Integer> productToDeleteIndex = getProductIndex(product);
        productToDeleteIndex.ifPresent(index -> {
            products.set(index, null);
            actProducts -= 1;
        });

    }

    public void addProduct(Product product) {
        products.add(product);
        maxProducts = Math.max(maxProducts, products.size());
        actProducts += 1;
    }

    public void createIndexForProducts() {
        for (int i = 0; i < products.size(); i += 1) {
            Product product = products.get(i);
            productsMap.put(product.name(), i);
        }
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
