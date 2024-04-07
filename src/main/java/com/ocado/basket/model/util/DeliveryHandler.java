package com.ocado.basket.model.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveryHandler {
    public static Map<String, List<String>> deliveryOptionsForProducts = new HashMap<>();
    public static int setDeliveryOptions(String absolutePathToConfigFile) throws FileNotFoundException {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(absolutePathToConfigFile)){

            Object obj = parser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;

            for (Object key : jsonObject.keySet()) {
                String product = (String) key;
                List<String> deliveryOption = (List<String>) jsonObject.get(key);
                deliveryOptionsForProducts.put(product, deliveryOption);
            }

        } catch (IOException | ParseException e) {
            throw new FileNotFoundException("Configuration file not found: " + absolutePathToConfigFile);
        }
        return 1;
    }
}
