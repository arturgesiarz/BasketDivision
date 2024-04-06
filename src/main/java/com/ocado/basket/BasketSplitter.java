package com.ocado.basket;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class BasketSplitter {
    private final Map<String, List<String>> deliveryOptionsForProducts = new HashMap<>();

    public BasketSplitter(String absolutePathToConfigFile) {
        setDeliveryOptions(absolutePathToConfigFile);
    }

    private void setDeliveryOptions(String absolutePathToConfigFile) {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(absolutePathToConfigFile)){

            Object obj = parser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;

            for (Object key : jsonObject.keySet()) {
                String product = (String) key;
                List<String> deliveryOption = (List<String>) jsonObject.get(key);
                deliveryOptionsForProducts.put(product, deliveryOption);
            }

        } catch (IOException | ParseException  e) {
            e.printStackTrace();
        }
    }

    public Map<String, List<String>> split(List<String> items) {
        return null;
    }

    public Map<String, List<String>> getDeliveryOptionsForProducts() {
        return Collections.unmodifiableMap(deliveryOptionsForProducts);
    }

    public static void main(String[] args) {
        String absolutPathTOConfigFile = "/Users/arturgesiarz/IdeaProjects/Java/BasketDivision/src/main/resources/config.json";
        BasketSplitter basketSplitter = new BasketSplitter(absolutPathTOConfigFile);
        Map<String, List<String>> deliveryOptionTest = basketSplitter.getDeliveryOptionsForProducts();
        deliveryOptionTest.get("Bread - Crumbs, Bulk").forEach(System.out::println);
    }
}
