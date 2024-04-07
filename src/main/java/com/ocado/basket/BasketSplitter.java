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
                "Cookies Oatmeal Raisin",
                "Cheese Cloth",
                "English Muffin",
                "Ecolab - Medallion",
                "Chocolate - Unsweetened",
                "Shrimp - 21/25, Peel And Deviened",
                "Beer - Alexander Kieths, Pale Ale",
                "Tea - Apple Green Tea",
                "Bread - Crumbs, Bulk",
                "Cheese - St. Andre",
                "Sole - Dover, Whole, Fresh",
                "Sugar - Cubes",
                "Otomegusa Dashi Konbu",
                "Flour - Buckwheat, Dark",
                "Bagel - Whole White Sesame",
                "Pork Ham Prager",
                "Salt - Rock, Course",
                "Yogurt - Cherry, 175 Gr",
                "Beef Cheek Fresh",
                "Brandy - Bar",
                "Juice - Ocean Spray Cranberry",
                "Wine - Port Late Bottled Vintage",
                "V8 Splash Strawberry Banana",
                "Gingerale - Diet - Schweppes",
                "Peach - Fresh",
                "Puree - Strawberry",
                "Garlic - Peeled",
                "Pasta - Fusili Tri - Coloured",
                "Onions - White",
                "Sauce - Salsa",
                "Bread - Petit Baguette",
                "Wine - Champagne Brut Veuve",
                "Capers - Ox Eye Daisy",
                "Sauce - Mint",
                "Beer - Muskoka Cream Ale",
                "Fish - Soup Base, Bouillon",
                "Cabbage - Nappa",
                "Spinach - Frozen",
                "Pork Salted Bellies",
                "Cheese - Mix",
                "Soup - Campbells Mac N Cheese",
                "Wine - Sherry Dry Sack, William",
                "Puree - Guava",
                "Wine - Fontanafredda Barolo",
                "Fudge - Chocolate Fudge",
                "Flavouring - Rum",
                "Energy Drink - Redbull 355ml",
                "Mix - Cocktail Ice Cream",
                "Numi - Assorted Teas",
                "Carbonated Water - Raspberry",
                "Haggis",
                "The Pop Shoppe - Grape",
                "Cake - Miini Cheesecake Cherry",
                "Ocean Spray - Ruby Red",
                "Appetizer - Escargot Puff",
                "Mustard - Dry, Powder",
                "Cookies - Englishbay Wht",
                "Nantucket Apple Juice",
                "Pork - Hock And Feet Attached",
                "Flower - Daisies",
                "Garbage Bags - Clear",
                "Vinegar - Red Wine",
                "Crush - Orange, 355ml",
                "Dried Peach",
                "Fond - Chocolate",
                "Cloves - Ground",
                "Nantucket - Pomegranate Pear",
                "Gatorade - Lemon Lime",
                "Longan",
                "Butter - Salted, Micro",
                "Emulsifier",
                "Nut - Almond, Blanched, Whole",
                "Chickhen - Chicken Phyllo",
                "Pepper - Green, Chili",
                "Oxtail - Cut",
                "Pineapple - Canned, Rings",
                "Syrup - Monin - Blue Curacao",
                "Corn Syrup",
                "Cookie - Oreo 100x2",
                "Mushroom - Porcini Frozen",
                "Wine - Magnotta - Cab Sauv",
                "Table Cloth 54x72 White",
                "Pepper - Julienne, Frozen",
                "Cheese - Sheep Milk",
                "Pepper - Red, Finger Hot",
                "Tart - Raisin And Pecan",
                "Compound - Mocha",
                "Apples - Spartan",
                "Steam Pan - Half Size Deep",
                "Wakami Seaweed",
                "Bag Clear 10 Lb",
                "Bread - Flat Bread",
                "Juice - Apple, 1.36l",
                "Cake Circle, Paprus",
                "Cocoa Butter",
                "Longos - Chicken Curried",
                "Oil - Olive, Extra Virgin",
                "Lamb - Whole, Fresh",
                "Dc Hikiage Hira Huba",
                "Beans - Green"
        );  // 12 ms

        // Timer
        long startTime = System.currentTimeMillis();

        Map<String, List<String>> result = basketSplitter.split(items);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        basketSplitter.printSplit(result);

        System.out.println("Time: " + duration + " ms");
    }
}
