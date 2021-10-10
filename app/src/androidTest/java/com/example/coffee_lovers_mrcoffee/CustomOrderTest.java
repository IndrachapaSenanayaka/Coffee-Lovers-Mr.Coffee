package com.example.coffee_lovers_mrcoffee;

import junit.framework.TestCase;

public class CustomOrderTest extends TestCase {

    CustomOrder customOrder;
    public void setUp() throws Exception {
        super.setUp();
        customOrder = new CustomOrder();
    }

    public void testTotalPriceWhenSizeChange() {
    }

    public void testTotalPriceWhenMilkChange() {
    }

    public void testTotalPriceWhenEspressoChange() {
    }

    public void testTotalPriceWhenFlavorsChange() {
    }

    public void testTotalPrice() {
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;

        float result = customOrder.TotalPrice(a,b,c,d);

        float expValue = 205.00f;

        assertEquals(expValue,result);
    }
}