package com.pahanaedu.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void testConstructorAndGetters() {
        Item item = new Item(101, "Book", 250.50);

        assertEquals(101, item.getItemId());
        assertEquals("Book", item.getName());
        assertEquals(250.50, item.getPrice());
    }

    @Test
    void testSetters() {
        Item item = new Item(102, "Pen", 50.00);

        item.setItemId(103);
        item.setName("Notebook");
        item.setPrice(75.25);

        assertEquals(103, item.getItemId());
        assertEquals("Notebook", item.getName());
        assertEquals(75.25, item.getPrice());
    }

    @Test
    void testNegativePrice() {
        Item item = new Item(104, "Marker", -100.00);
        assertEquals(-100.00, item.getPrice(), "Should allow negative (no validation)");
    }

    @Test
    void testZeroPrice() {
        Item item = new Item(105, "Eraser", 0.00);
        assertEquals(0.00, item.getPrice(), "Price should be zero");
    }
}
