package com.pahanaedu.dao;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.pahanaedu.dao.ItemDAO;
import com.pahanaedu.model.Item;
import java.util.List;

public class ItemDAOTest {

    private ItemDAO itemDAO;

    @Before
    public void setUp() {
        itemDAO = new ItemDAO();
    }

    @Test
    public void testAddItem() {
        Item item = new Item(0, "Notebook", 250.00);
        boolean result = itemDAO.addItem(item);
        assertTrue(result);
    }

    @Test
    public void testUpdateItem() {
        // Assuming item with ID 1 exists
        Item item = new Item(1, "Notebook Updated", 300.00);
        boolean result = itemDAO.updateItem(item);
        assertTrue(result);
    }

    @Test
    public void testDeleteItem() {
        // Assuming item with ID 1 exists
        boolean result = itemDAO.deleteItem(1);
        assertTrue(result);
    }

    @Test
    public void testGetAllItems() {
        List<Item> items = itemDAO.getAllItems();
        assertNotNull(items);
        assertTrue(items.size() >= 0);
    }

    @Test
    public void testGetItemById() {
        Item item = itemDAO.getItemById(1);
        assertNotNull(item);
        assertEquals(1, item.getItemId());
    }
}
