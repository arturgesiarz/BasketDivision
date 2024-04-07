package com.ocado.basket.model;
import java.util.*;
import java.util.stream.IntStream;

public class Basket {
    private int productsHaveSupplier = 0;
    private final List<Product> products = new ArrayList<>();
    private final List<Supplier> suppliers = new ArrayList<>();
    private final Map<String, Integer> productsMap = new HashMap<>();
    private final Map<String, Integer> deliveryMap = new HashMap<>();

    public Basket(List<String> items, Map<String,
            List<String>> deliveryOptionsForProducts) {

        createIndexForProductsAndSupplierMap(items, deliveryOptionsForProducts);
        createProductsList();
        createSuppliersList();
        assignProductsForSupplier(items, deliveryOptionsForProducts);
        createAllIndexForProductsForSuppliers();

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

    private void createIndexForProductsAndSupplierMap(List<String> items, Map<String,
            List<String>> deliveryOptionsForProducts) {
        int productMapIterator = 0;
        int deliveryMapIterator = 0;

        for (String productName : items) {  // O(n^2)
            if (!productsMap.containsKey(productName)) {
                productsMap.put(productName, productMapIterator);
                productMapIterator += 1;
            }

            for (String deliveryName : deliveryOptionsForProducts.get(productName)) {
                if (!deliveryMap.containsKey(deliveryName)) {
                    deliveryMap.put(deliveryName, deliveryMapIterator);
                    deliveryMapIterator += 1;
                }
            }
        }
    }

    private void createProductsList() {
        IntStream.range(0, productsMap.size()).forEach(i -> products.add(null));

        productsMap.forEach((key, value) -> {
            int index = value;
            Product product = new Product(key);
            products.set(index, product);
        });
    }

    private void createSuppliersList() {
        IntStream.range(0, deliveryMap.size()).forEach(i -> suppliers.add(null));

        deliveryMap.forEach((key, value) -> {
            int index = value;
            Supplier supplier = new Supplier(key);
            suppliers.set(index, supplier);
        });
    }

    public Optional<Product> getProduct(String productName) {
        Optional<Integer> productIndex = Optional.of(productsMap.get(productName));
        return productIndex.map(products::get);
    }


    public Optional<Supplier> getSupplier(String supplierName) {
        Optional<Integer> supplierIndex = Optional.of(deliveryMap.get(supplierName));
        return supplierIndex.map(suppliers::get);
    }

    private void addProductForSupplier(String supplierName, String productName) {
        Optional<Supplier> supplier = getSupplier(supplierName);
        Optional<Product> product = getProduct(productName);

        if (supplier.isPresent() && product.isPresent()) {
            supplier.get().addProduct(product.get());
        }
    }

    private void assignProductsForSupplier(List<String> items, Map<String,
            List<String>> deliveryOptionsForProducts) {

        for (String productName : items) {
            for (String supplierName : deliveryOptionsForProducts.get(productName)) {
                addProductForSupplier(supplierName, productName);
            }
        }
    }

    private void createAllIndexForProductsForSuppliers() {
        suppliers.forEach(Supplier::createIndexForProducts);
    }

    public Map<String, List<String>> getResult() {
        Map<String, List<String>> result = new HashMap<>();

        for (Supplier supplier : suppliers) {
            if (supplier.getActProductsNumber() > 0) {
                List<String> productNameList = new ArrayList<>();
                for (Product product : supplier.getProducts()) {
                    if (product != null) {
                        productNameList.add(product.getName());
                    }
                }
                result.put(supplier.getName(), productNameList);
            }
        }

        return result;
    }


}
