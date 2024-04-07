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

        Basket basket = new Basket(items, deliveryOptionsForProducts);

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

    public Map<String, List<String>> getDeliveryOptionsForProducts() {
        return Collections.unmodifiableMap(deliveryOptionsForProducts);
    }

    public Set<String> getAllDeliveryOptions() {
        return Collections.unmodifiableSet(allDeliveryOptions);
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
        Map<String, List<String>> deliveryOptionTest = basketSplitter.getDeliveryOptionsForProducts();
        Set<String> deliveryOptionsAllTest = basketSplitter.getAllDeliveryOptions();
        List<String> items = Arrays.asList(
                "Fond - Chocolate",
                "Chocolate - Unsweetened",
                "Nut - Almond, Blanched, Whole",
                "Haggis",
                "Mushroom - Porcini Frozen",
                "Cake - Miini Cheesecake Cherry",
                "Sauce - Mint"
        );

        // Timer
        long startTime = System.currentTimeMillis();

        Map<String, List<String>> result = basketSplitter.split(items);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        basketSplitter.printSplit(result);

        System.out.println("Time: " + duration + " ms");
    }
}
