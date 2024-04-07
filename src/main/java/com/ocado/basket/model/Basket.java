package com.ocado.basket.model;
import com.ocado.basket.model.util.DeliveryHandler;

import java.util.*;
import java.util.stream.IntStream;

public class Basket {
    private static int productsHaveSupplier = 0;
    private static List<Product> products = new ArrayList<>();
    private static List<Supplier> suppliers = new ArrayList<>();
    private static Map<String, Integer> productsMap = new HashMap<>();
    private static Map<String, Integer> deliveryMap = new HashMap<>();

    public static void startCalculatingTheBestSplit(List<String> items) {
        int pointerToSupplier = 0;
        while (getProductsHaveSupplier() != items.size()) {

            getSuppliers().sort(Comparator.comparingInt(Supplier::getActProductsNumber).reversed());
            Supplier supplier = getSuppliers().get(pointerToSupplier);

            for (Product product : supplier.getProducts()) {
                if (product != null) {
                    for (Supplier supplierToChange : getSuppliers()) {
                        if (!supplierToChange.equals(supplier)) {
                            supplierToChange.deleteProduct(product);
                        }
                    }
                    assignProduct();
                }
            }
            pointerToSupplier += 1;
        }
    }

    public static List<Supplier> getSuppliers() {
        return suppliers;
    }

    public static void assignProduct() {
        productsHaveSupplier += 1;
    }

    public static int getProductsHaveSupplier() {
        return productsHaveSupplier;
    }

    public static void createIndexForProductsAndSupplierMap(List<String> items) {
        int productMapIterator = 0;
        int deliveryMapIterator = 0;

        for (String productName : items) {
            if (!productsMap.containsKey(productName)) {
                productsMap.put(productName, productMapIterator);
                productMapIterator += 1;
            }

            for (String deliveryName : DeliveryHandler.deliveryOptionsForProducts.get(productName)) {
                if (!deliveryMap.containsKey(deliveryName)) {
                    deliveryMap.put(deliveryName, deliveryMapIterator);
                    deliveryMapIterator += 1;
                }
            }
        }
    }

    public static void createProductsList() {
        IntStream.range(0, productsMap.size()).forEach(i -> products.add(null));

        productsMap.forEach((key, value) -> {
            int index = value;
            Product product = new Product(key);
            products.set(index, product);
        });
    }

    public static void createSuppliersList() {
        IntStream.range(0, deliveryMap.size()).forEach(i -> suppliers.add(null));

        deliveryMap.forEach((key, value) -> {
            int index = value;
            Supplier supplier = new Supplier(key);
            suppliers.set(index, supplier);
        });
    }

    static public Optional<Product> getProduct(String productName) {
        Optional<Integer> productIndex = Optional.of(productsMap.get(productName));
        return productIndex.map(products::get);
    }


    static public Optional<Supplier> getSupplier(String supplierName) {
        Optional<Integer> supplierIndex = Optional.of(deliveryMap.get(supplierName));
        return supplierIndex.map(suppliers::get);
    }

    private static void addProductForSupplier(String supplierName, String productName) {
        Optional<Supplier> supplier = getSupplier(supplierName);
        Optional<Product> product = getProduct(productName);

        if (supplier.isPresent() && product.isPresent()) {
            supplier.get().addProduct(product.get());
        }
    }

    public static void assignProductsForSupplier(List<String> items) {

        for (String productName : items) {
            for (String supplierName : DeliveryHandler.deliveryOptionsForProducts.get(productName)) {
                addProductForSupplier(supplierName, productName);
            }
        }
    }

    static public void createAllIndexForProductsForSuppliers() {
        suppliers.forEach(Supplier::createIndexForProducts);
    }

    static public Map<String, List<String>> getResult() {
        Map<String, List<String>> result = new HashMap<>();

        suppliers.stream()
                .filter(supplier -> supplier.getActProductsNumber() > 0)
                .forEach(supplier -> {
                    List<String> productNameList = new ArrayList<>();
                    supplier.getProducts().stream()
                            .filter(Objects::nonNull)
                            .map(Product::name)
                            .forEach(productNameList::add);
                    result.put(supplier.getName(),productNameList);
                });

        return result;
    }

    static public void resetState() {
        productsHaveSupplier = 0;
        products = new ArrayList<>();
        suppliers = new ArrayList<>();
        productsMap = new HashMap<>();
        deliveryMap = new HashMap<>();
    }


}
