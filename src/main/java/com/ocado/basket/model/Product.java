package com.ocado.basket.model;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private final String name;
    private final List<Supplier> possibleSupplier = new ArrayList<>();
    private Supplier supplier;

    public Product(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public void addPossibleSupplier(Supplier supplier) {
        possibleSupplier.add(supplier);
    }

    public List<Supplier> getPossibleSupplier() {
        return possibleSupplier;
    }
}
