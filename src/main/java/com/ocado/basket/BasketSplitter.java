package com.ocado.basket;
import java.util.*;

import com.ocado.basket.model.Basket;
import com.ocado.basket.model.Product;
import com.ocado.basket.model.Supplier;
import com.ocado.basket.model.util.DeliveryHandler;

public class BasketSplitter {

    public BasketSplitter(String absolutePathToConfigFile) {
        DeliveryHandler.setDeliveryOptions(absolutePathToConfigFile);
    }

    public Map<String, List<String>> split(List<String> items) {

        Basket basket = new Basket(items, DeliveryHandler.deliveryOptionsForProducts);

        int pointerToSupplier = 0;

        while (basket.getProductsHaveSupplier() != items.size()) {

            basket.getSuppliers().sort(Comparator.comparingInt(Supplier::getActProductsNumber).reversed());
            Supplier supplier = basket.getSuppliers().get(pointerToSupplier);

            for (Product product : supplier.getProducts()) {
                if (product != null) {
                    for (Supplier supplierToChange : basket.getSuppliers()) {
                        if (!supplierToChange.equals(supplier)) {
                            supplierToChange.deleteProduct(product);
                        }
                    }
                    basket.assignProduct();
                }
            }
            pointerToSupplier += 1;
        }

        return basket.getResult();
    }


    public void printSplit (Map<String, List<String>> result) {
        for (Map.Entry<String, List<String>> entry : result.entrySet()) {
            System.out.println("--------------");
            System.out.println("Delivery name: " + entry.getKey());
            System.out.println("Products: ");
            for (String product : entry.getValue()) {
                System.out.println(product);
            }

        }
    }

    public static void main(String[] args) {

        String absolutPathTOConfigFile = "/Users/arturgesiarz/IdeaProjects/Java/BasketDivision/src/main/resources/config.json";
        BasketSplitter basketSplitter = new BasketSplitter(absolutPathTOConfigFile);
        List<String> items2 = Arrays.asList(
                "Fond - Chocolate",
                "Chocolate - Unsweetened",
                "Nut - Almond, Blanched, Whole",
                "Haggis",
                "Mushroom - Porcini Frozen",
                "Cake - Miini Cheesecake Cherry",
                "Sauce - Mint",
                "Longan",
                "Bag Clear 10 Lb",
                "Nantucket - Pomegranate Pear",
                "Puree - Strawberry",
                "Numi - Assorted Teas",
                "Apples - Spartan",
                "Garlic - Peeled",
                "Cabbage - Nappa",
                "Bagel - Whole White Sesame",
                "Tea - Apple Green Tea"
        );

        // Timer
        long startTime = System.currentTimeMillis();

        Map<String, List<String>> result = basketSplitter.split(items2);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        System.out.println("Time: " + duration + " ms");
    }
}
