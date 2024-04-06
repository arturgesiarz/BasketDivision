package com.ocado.basket;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.ocado.basket.model.Basket;
import com.ocado.basket.model.Product;
import com.ocado.basket.model.Supplier;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class BasketSplitter {
    private final Map<String, List<String>> deliveryOptionsForProducts = new HashMap<>();
    private final Set<String> allDeliveryOptions = new HashSet<>();

    public BasketSplitter(String absolutePathToConfigFile) {
        setDeliveryOptions(absolutePathToConfigFile);
        findAllDeliveryOptions();
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

    private void findAllDeliveryOptions() {
        deliveryOptionsForProducts.values().forEach(allDeliveryOptions::addAll);
    }


    public Map<String, List<String>> split(List<String> items) {

        Basket basket = new Basket();

        for (String item : items) {
            basket.addProductToBasket(item);

            for (String deliveryName : deliveryOptionsForProducts.get(item)) {
                basket.addSupplier(deliveryName);
                basket.addProductForSupplier(deliveryName, item);
            }
        }





        return null;
    }

    public Map<String, List<String>> getDeliveryOptionsForProducts() {
        return Collections.unmodifiableMap(deliveryOptionsForProducts);
    }

    public Set<String> getAllDeliveryOptions() {
        return Collections.unmodifiableSet(allDeliveryOptions);
    }

    public static void main(String[] args) {
        String absolutPathTOConfigFile = "/Users/arturgesiarz/IdeaProjects/Java/BasketDivision/src/main/resources/config.json";
        BasketSplitter basketSplitter = new BasketSplitter(absolutPathTOConfigFile);
        Map<String, List<String>> deliveryOptionTest = basketSplitter.getDeliveryOptionsForProducts();
        Set<String> deliveryOptionsAllTest = basketSplitter.getAllDeliveryOptions();
        List<String> items = Arrays.asList("Cocoa Butter","Tart - Raisin And Pecan",
                "Table Cloth 54x72 White");
        basketSplitter.split(items);
    }
}
