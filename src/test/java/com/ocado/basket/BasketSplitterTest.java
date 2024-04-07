package com.ocado.basket;

import com.ocado.basket.model.Basket;
import com.ocado.basket.model.util.DeliveryHandler;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

public class BasketSplitterTest {

    @Test
    public void isConfigFileIsReadCorrectly() {
        //given

        // - existing path
        String absolutCorrectPathToConfigFile = "src/main/resources/config.json";

        // - non-existent path
        String absolutIncorrectPathToConfigFile = "src/main/resources/not-existing-config2137.cpp";

        //when, then
        assertThrows(FileNotFoundException.class, () -> DeliveryHandler.setDeliveryOptions(absolutIncorrectPathToConfigFile));
        try {
            assertEquals(1, DeliveryHandler.setDeliveryOptions(absolutCorrectPathToConfigFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void isBasketIsDividedIntoMinNumOfCouriers() {
        //given

        //when

        //then
    }

    @Test
    public void isBasketIsDividedOptimally() {
        //given

        //when

        //then
    }



}