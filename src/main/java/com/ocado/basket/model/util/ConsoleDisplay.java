package com.ocado.basket.model.util;

import java.util.List;
import java.util.Map;

public class ConsoleDisplay {
    public static void printSplit (Map<String, List<String>> result) {
        for (Map.Entry<String, List<String>> entry : result.entrySet()) {
            System.out.println("--------------");
            System.out.println("Delivery name: " + entry.getKey());
            System.out.println("Products: ");
            for (String product : entry.getValue()) {
                System.out.println(product);
            }

        }
    }
}
