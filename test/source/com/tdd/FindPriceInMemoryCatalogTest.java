package com.tdd;

import java.util.Collections;
import java.util.Map;

/**
 * Created by darkorajin on 29/12/2016.
 */
public class FindPriceInMemoryCatalogTest extends FindPriceInMemoryCatalogContract {

    @Override
    protected Catalog catalogWith(String barcode, Price price) {
        return new InMemoryCatalog(Collections.singletonMap(barcode, price));
    }

    @Override
    protected Catalog catalogWithout(String barcodeToAvoid) {
        return new InMemoryCatalog(Collections.singletonMap("anything but " + barcodeToAvoid, Price.cents(0)));
    }

    public static class InMemoryCatalog implements Catalog {
        private Map<String, Price> pricesByBarcode;

        public InMemoryCatalog(Map<String, Price> pricesByBarcode) {

            this.pricesByBarcode = pricesByBarcode;
        }

        public Price findPrice(String barcode) {
            return pricesByBarcode.get(barcode);
        }
    }
}
