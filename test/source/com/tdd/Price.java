package com.tdd;

/**
 * Created by darkorajin on 29/12/2016.
 */
public class Price {

    public static Price cents(int centsValue) {
        return new Price();
    }

    @Override
    public String toString() {
        return "a Price";
    }
}
