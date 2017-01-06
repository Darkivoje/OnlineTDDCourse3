package com.tdd;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by darkorajin on 06/01/2017.
 */
public abstract class FindPriceInMemoryCatalogContract {
    @Test
    public void productFound() throws Exception {
        Price foundPrice = Price.cents(1250);
        Catalog catalog = catalogWith("12345", foundPrice);
        Assert.assertEquals(foundPrice, catalog.findPrice("12345"));

    }

    protected abstract Catalog catalogWith(String barcode, Price price);

    @Test
    public void productNotFound() throws Exception {
        Catalog catalog = catalogWithout("12345");
        Assert.assertEquals(null, catalog.findPrice("12345"));

    }

    protected abstract Catalog catalogWithout(String barcodeToAvoid);
}
