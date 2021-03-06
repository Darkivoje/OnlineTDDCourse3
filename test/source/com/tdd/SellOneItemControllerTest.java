package com.tdd;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by darkorajin on 29.09.16.
 */
public class SellOneItemControllerTest {
    @Rule
    public final JUnitRuleMockery context = new JUnitRuleMockery();

    @Test
    public void productFound() throws Exception {
        final Catalog catalog = context.mock(Catalog.class);
        final Display display = context.mock(Display.class);
        final Price irrelevantPrice = Price.cents(795);

        context.checking(new Expectations() {{
            //STUBBING IF I CALL CONTROLLER WITH SOMETHING, HE GIVES ME SOMETHING BACK
            allowing(catalog).findPrice(with("::product found::"));
            will(returnValue(irrelevantPrice));

            oneOf(display).displayPrice(with(irrelevantPrice));
        }});

        SaleController saleController = new SaleController(catalog, display);
        saleController.onBarcode("::product found::");

        //

    }

    @Test
    public void productNotFound() throws Exception {
        final Catalog catalog = context.mock(Catalog.class);
        final Display display = context.mock(Display.class);

        context.checking(new Expectations() {{
            allowing(catalog).findPrice(with("::product not found::"));
            will(returnValue(null));

            oneOf(display).displayProductNotFoundMessage(with("::product not found::"));
        }});

        SaleController saleController = new SaleController(catalog, display);
        saleController.onBarcode("::product not found::");

    }

    @Test
    public void emptyBarcode() throws Exception {
        final Display display = context.mock(Display.class);

        context.checking(new Expectations() {{

            oneOf(display).displayEmptyBarcodeMessage();
        }});

        SaleController saleController = new SaleController(null, display);
        saleController.onBarcode("");
    }

    public interface Display {
        void displayPrice(Price price);

        void displayProductNotFoundMessage(String barcodeNotFound);

        void displayEmptyBarcodeMessage();
    }

    public static class SaleController {
        private Display display;
        private Catalog catalog;

        public SaleController(Catalog catalog, Display display) {
            this.display = display;
            this.catalog = catalog;
        }

        public void onBarcode(String barcode) {
            if ("".equals(barcode)) {
                display.displayEmptyBarcodeMessage();
                return;
            }

            Price price = catalog.findPrice(barcode);
            if (price == null)
                display.displayProductNotFoundMessage(barcode);
            else
                display.displayPrice(price);
        }
    }

}
