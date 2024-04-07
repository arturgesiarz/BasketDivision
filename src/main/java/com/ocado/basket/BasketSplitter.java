package com.ocado.basket;
import java.io.FileNotFoundException;
import java.util.*;

import com.ocado.basket.model.Basket;
import com.ocado.basket.model.util.ConsoleDisplay;
import com.ocado.basket.model.util.DeliveryHandler;

public class BasketSplitter {

    public BasketSplitter(String absolutePathToConfigFile) throws FileNotFoundException {
        DeliveryHandler.setDeliveryOptions(absolutePathToConfigFile);
    }

    public Map<String, List<String>> split(List<String> items) {

        Basket.createIndexForProductsAndSupplierMap(items);
        Basket.createProductsList();
        Basket.createSuppliersList();
        Basket.assignProductsForSupplier(items);
        Basket.createAllIndexForProductsForSuppliers();
        Basket.startCalculatingTheBestSplit(items);

        Map<String, List<String>> result = Basket.getResult();

        Basket.resetState();

        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String absolutPathTOConfigFile = "src/main/resources/config.json";
        BasketSplitter basketSplitter = new BasketSplitter(absolutPathTOConfigFile);
        List<String> items2 = Arrays.asList(
                "Cocoa Butter",
                "Tart - Raisin And Pecan",
                "Table Cloth 54x72 White",
                "Flower - Daisies",
                "Fond - Chocolate",
                "Cookies - Englishbay Wht"

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
