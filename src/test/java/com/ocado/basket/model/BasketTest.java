package com.ocado.basket.model;

import com.ocado.basket.model.util.DeliveryHandler;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BasketTest  {

    @Test
    public void isCreateProductsListCorrectly() throws FileNotFoundException {
        //given
        String absolutPathTOConfigFile = "src/main/resources/config.json";
        List<String> items = Arrays.asList(
          "Fond - Chocolate",
          "Sugar - Cubes",
          "Wine - Sherry Dry Sack, William"
        );

        //when
        DeliveryHandler.setDeliveryOptions(absolutPathTOConfigFile);
        Basket.createIndexForProductsAndSupplierMap(items);
        Basket.createProductsList();
        List<Product> products = Basket.getProducts();
        Basket.resetState();
        List<String> productsSol = products.stream().map(Product::name).toList();

        //then
        assertEquals(items, productsSol);
    }

    @Test
    public void isCreateSuppliersListCorrectly() throws FileNotFoundException {
        //given
        String absolutPathTOConfigFile = "src/main/resources/config.json";
        List<String> items = Arrays.asList(
                "Fond - Chocolate",
                "Sugar - Cubes",
                "Wine - Sherry Dry Sack, William"
        );
        List<String> deliveries = Arrays.asList(
                "Pick-up point",
                "Express Collection",
                "Mailbox delivery",
                "Next day shipping",
                "Same day delivery"

        );

        //when
        DeliveryHandler.setDeliveryOptions(absolutPathTOConfigFile);
        Basket.createIndexForProductsAndSupplierMap(items);
        Basket.createSuppliersList();
        List<Supplier> suppliers = Basket.getSuppliers();
        Basket.resetState();
        List<String> suppliersSol = suppliers.stream().map(Supplier::getName).toList();

        //then
        assertEquals(deliveries, suppliersSol);
    }

    @Test
    public void isAssignProductsForSupplierCorrectly() throws FileNotFoundException {
        //given
        String absolutPathTOConfigFile = "src/main/resources/config.json";
        List<String> items = Arrays.asList(
                "Fond - Chocolate",
                "Sugar - Cubes",
                "Wine - Sherry Dry Sack, William"
        );
        List<List<String>> productsForDeliveries = Arrays.asList(
                List.of(
                        "Fond - Chocolate"
                ),
                List.of(
                        "Fond - Chocolate"
                ),
                List.of(
                        "Fond - Chocolate",
                        "Wine - Sherry Dry Sack, William"
                ),
                List.of(
                        "Sugar - Cubes"
                ),
                List.of(
                        "Sugar - Cubes"
                )
        );

        //when
        DeliveryHandler.setDeliveryOptions(absolutPathTOConfigFile);

        Basket.createIndexForProductsAndSupplierMap(items);
        Basket.createProductsList();
        Basket.createSuppliersList();
        Basket.assignProductsForSupplier(items);

        List<Supplier> suppliers = Basket.getSuppliers();

        List<List<String>> result = suppliers.stream().map(supplier -> supplier.getProducts()
                .stream().map(Product::name)
                .toList()).toList();

        Basket.resetState();

        //then
        assertTrue(result.get(0).containsAll(productsForDeliveries.get(0)));
        assertTrue(result.get(1).containsAll(productsForDeliveries.get(1)));
        assertTrue(result.get(2).containsAll(productsForDeliveries.get(2)));
        assertTrue(result.get(3).containsAll(productsForDeliveries.get(3)));
        assertTrue(result.get(4).containsAll(productsForDeliveries.get(4)));
    }

}