package com.ocado.basket.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SupplierTest {

    @Test
    public void isAddProductCorrectly() {
        //given
        Supplier supplier = new Supplier("Fedex");

        Product product1 = new Product("PS5");
        Product product2 = new Product("DYSON Detect Absolute");
        Product product3 = new Product("Diablo chair");

        //when
        supplier.addProduct(product1);
        supplier.addProduct(product2);
        supplier.addProduct(product3);

        supplier.createIndexForProducts();

        //then
        List<Product> productsCorrect = Arrays.asList(
                product1,
                product2,
                product3
        );
        List<Product> productsReceived = supplier.getProducts();

        assertEquals(productsReceived, productsCorrect);
    }

    @Test
    public void isDeleteProductCorrectly() {
        //given
        Supplier supplier = new Supplier("Fedex");

        Product product1 = new Product("PS5");
        Product product2 = new Product("DYSON Detect Absolute");
        Product product3 = new Product("Diablo chair");

        //when
        supplier.addProduct(product1);
        supplier.addProduct(product2);
        supplier.addProduct(product3);

        supplier.createIndexForProducts();

        supplier.deleteProduct(product1);
        supplier.deleteProduct(product2);
        supplier.deleteProduct(product3);

        //then
        assertEquals(0,supplier.getActProductsNumber());
    }


}