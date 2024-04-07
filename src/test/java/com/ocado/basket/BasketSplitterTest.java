package com.ocado.basket;
import com.ocado.basket.model.util.DeliveryHandler;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class BasketSplitterTest {
    @Test
    public void whetherDetectsThatConfigIsCorrect() throws FileNotFoundException {
        // given
        String absolutCorrectPathToConfigFile = "src/main/resources/config.json";

        // when, then
        assertEquals(1, DeliveryHandler.setDeliveryOptions(absolutCorrectPathToConfigFile));
    }

    @Test
    public void whetherDetectsThatConfigIsIncorrect()  {
        //given
        String absolutIncorrectPathToConfigFile = "src/main/resources/not-existing-config2137.cpp";

        //when, then
        assertThrows(FileNotFoundException.class, () -> DeliveryHandler.setDeliveryOptions(absolutIncorrectPathToConfigFile));
    }

    @Test
    public void whetherBasketIsDividedIntoThreeCouriers() throws FileNotFoundException {
        //given
        String absolutPathTOConfigFile = "src/main/resources/config.json";

        BasketSplitter basketSplitter = new BasketSplitter(absolutPathTOConfigFile);
        List<String> items = Arrays.asList(
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

        List<String> correctCouriers = Arrays.asList(
                "Same day delivery",
                "Courier",
                "Express Collection"
        );

        //when
        Map<String, List<String>> result = basketSplitter.split(items);

        //then
        assertEquals(3, result.size());  // size
        assertTrue(result.keySet().containsAll(correctCouriers));

    }

    @Test
    public void whetherBasketIsDividedIntoTwoCouriers() throws FileNotFoundException {
        //given
        String absolutPathTOConfigFile = "src/main/resources/config.json";

        BasketSplitter basketSplitter = new BasketSplitter(absolutPathTOConfigFile);
        List<String> items = Arrays.asList(
                "Cocoa Butter",
                "Tart - Raisin And Pecan",
                "Table Cloth 54x72 White",
                "Flower - Daisies",
                "Fond - Chocolate",
                "Cookies - Englishbay Wht"
        );

        List<String> correctCouriers = Arrays.asList(
                "Courier",
                "Mailbox delivery"
        );

        //when
        Map<String, List<String>> result = basketSplitter.split(items);

        //then
        assertEquals(2, result.size());  // size
        assertTrue(result.keySet().containsAll(correctCouriers));

    }

    @Test
    public void whetherBasketIsDividedIntoOneCourier() throws FileNotFoundException {
        //given
        String absolutPathTOConfigFile = "src/main/resources/config.json";

        BasketSplitter basketSplitter = new BasketSplitter(absolutPathTOConfigFile);
        List<String> items = Arrays.asList(
                "Fond - Chocolate",
                "Chocolate - Unsweetened",
                "Nut - Almond, Blanched, Whole",
                "Haggis",
                "Mushroom - Porcini Frozen"
        );

        List<String> correctCouriers = List.of(
                "Pick-up point"
        );

        //when
        Map<String, List<String>> result = basketSplitter.split(items);

        //then
        assertEquals(1, result.size());  // size
        assertTrue(result.keySet().containsAll(correctCouriers));

    }


    @Test
    public void isBasketIsDividedOptimally() {
        //given

        //when

        //then
    }
}

