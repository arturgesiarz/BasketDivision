package com.ocado.basket;

import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BasketSplitterTest {

    @Test
    public void isConfigFileIsReadCorrectly() {
        //given
        String absolutPathTOConfigFile = "src/main/resources/config.jso";

        //when
        assertThrows(FileNotFoundException.class, () -> new BasketSplitter(absolutPathTOConfigFile));
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