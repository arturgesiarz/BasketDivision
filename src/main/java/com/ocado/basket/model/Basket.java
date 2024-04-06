package com.ocado.basket.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Basket {
    List<Product> products = new ArrayList<>();
    List<Supplier> suppliers = new ArrayList<>();
    public Basket() {
    }

    public void addProductToBasket(String productName) {
        Product product = new Product(productName);
        if (!products.contains(product)) {
            products.add(product);
        }
    }

    public Optional<Product> getProduct(String productName) {
        for (Product product : products) {
            if (product.getName().equals(productName)) {
                return Optional.of(product);
            }
        }
        return Optional.empty();
    }

    public void addSupplier(String supplierName) {
        Supplier supplier = new Supplier(supplierName);
        if (!suppliers.contains(supplier)) {
            suppliers.add(supplier);
        }
    }

    public Optional<Supplier> getSupplier(String supplierName) {
        for (Supplier supplier : suppliers) {
            if (supplier.getName().equals(supplierName)) {
                return Optional.of(supplier);
            }
        }
        return Optional.empty();
    }

    public void addProductForSupplier(String supplierName, String productName) {
        Optional<Supplier> supplier = getSupplier(supplierName);
        Optional<Product> product = getProduct(productName);

        if (supplier.isPresent() && product.isPresent()) {
            supplier.get().addProduct(product.get());
            product.get().addPossibleSupplier(supplier.get());

        }
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Supplier> getSuppliers() {
        return suppliers;
    }
}
