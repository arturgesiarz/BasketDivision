package com.ocado.basket.model;
import java.util.*;

public class Basket {
    private int productsHaveSupplier = 0;
    List<Product> products = new ArrayList<>();
    List<Supplier> suppliers = new ArrayList<>();
    public Basket() {
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public void assignProduct() {
        productsHaveSupplier += 1;
    }

    public int getProductsHaveSupplier() {
        return productsHaveSupplier;
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

    public Map<String, List<String>> getResult() {
        Map<String, List<String>> result = new HashMap<>();

        for (Supplier supplier : suppliers) {
            if (supplier.getProducts().size() > 0) {
                result.put(supplier.getName(), supplier.getProducts()
                        .stream()
                        .map(Product::getName)
                        .toList());
            }
        }

        return result;
    }


}
