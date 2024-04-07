package com.ocado.basket;
import java.util.*;

import com.ocado.basket.model.Basket;
import com.ocado.basket.model.util.ConsoleDisplay;
import com.ocado.basket.model.util.DeliveryHandler;

public class BasketSplitter {

    public BasketSplitter(String absolutePathToConfigFile) {
        DeliveryHandler.setDeliveryOptions(absolutePathToConfigFile);
    }

    public Map<String, List<String>> split(List<String> items) {

        Basket.createIndexForProductsAndSupplierMap(items);
        Basket.createProductsList();
        Basket.createSuppliersList();
        Basket.assignProductsForSupplier(items);
        Basket.createAllIndexForProductsForSuppliers();
        Basket.startCalculatingTheBestSplit(items);

        return Basket.getResult();
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

        ConsoleDisplay.printSplit(result);
    }
}
