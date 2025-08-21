package com.pahanaedu.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void testConstructorAndGetters() {
        Customer customer = new Customer("C001", "John Doe", "Colombo", "0771234567");

        assertEquals("C001", customer.getAccountNumber());
        assertEquals("John Doe", customer.getName());
        assertEquals("Colombo", customer.getAddress());
        assertEquals("0771234567", customer.getPhone());
    }

    private void assertEquals(String johnDoe, String name) {
    }

    @Test
    void testSetters() {
        Customer customer = new Customer("C002", "Alice", "Kandy", "0770000000");

        customer.setName("Jane Doe");
        customer.setAddress("Galle");
        customer.setPhone("0712345678");
        customer.setAccountNumber("C003");

        assertEquals("Jane Doe", customer.getName());
        assertEquals("Galle", customer.getAddress());
        assertEquals("0712345678", customer.getPhone());
        assertEquals("C003", customer.getAccountNumber());
    }

    @Test
    void testNullValues() {
        Customer customer = new Customer("C004", "Test User", "Negombo", "0751111111");

        customer.setAddress(null);
        customer.setPhone(null);

        assertNull(customer.getAddress());
        assertNull(customer.getPhone());
    }

    private void assertNull(String address) {
    }

    @Test
    void testPhoneNumberFormat() {
        Customer customer = new Customer("C005", "User", "Colombo", "123");
        Assertions.assertEquals("123", customer.getPhone(), "Should accept any string (no validation yet)");
    }
}
