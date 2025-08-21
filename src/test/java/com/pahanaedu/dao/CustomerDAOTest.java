package com.pahanaedu.dao;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import com.pahanaedu.dao.CustomerDAO;
import com.pahanaedu.model.Customer;
import java.util.List;

public class CustomerDAOTest {

    private CustomerDAO customerDAO;

    @Before
    public void setUp() {
        customerDAO = new CustomerDAO();
    }

    @Test
    public void testAddCustomer() {
        Customer customer = new Customer("AC123", "John Doe", "123 Street", "1234567890");
        boolean result = customerDAO.addCustomer(customer);
        assertTrue(result);
    }

    @Test
    public void testAddDuplicateCustomer() {
        Customer customer = new Customer("AC123", "John Doe", "123 Street", "1234567890");
        customerDAO.addCustomer(customer);
        boolean result = customerDAO.addCustomer(customer);
        assertFalse(result);
    }

    @Test
    public void testUpdateCustomer() {
        Customer customer = new Customer("AC123", "John Updated", "New Street", "0987654321");
        boolean result = customerDAO.updateCustomer(customer);
        assertTrue(result);
    }

    @Test
    public void testDeleteCustomer() {
        boolean result = customerDAO.deleteCustomer("AC123");
        assertTrue(result);
    }

    @Test
    public void testGetAllCustomers() {
        List<Customer> customers = customerDAO.getAllCustomers();
        assertNotNull(customers);
        assertTrue(customers.size() >= 0);
    }
}
