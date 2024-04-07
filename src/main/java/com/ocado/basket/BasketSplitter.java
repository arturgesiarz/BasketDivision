package com.ocado.basket;
import java.io.FileNotFoundException;
import java.util.*;

import com.ocado.basket.model.Basket;
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
        DeliveryHandler.resetState();

        return result;
    }
}
