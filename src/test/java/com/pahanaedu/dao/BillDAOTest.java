package com.pahanaedu.dao;

import org.junit.jupiter.api.*;
import java.sql.Timestamp;
import static org.junit.jupiter.api.Assertions.*;

class BillDAOTest {

    private BillDAO billDAO;

    @BeforeEach
    void setUp() {
        billDAO = new BillDAO();
    }

    @Test
    @DisplayName("Save valid bill")
    void testSaveBillValid() {
        String accountNumber = "C001";
        double totalAmount = 500.0;
        Timestamp billDate = new Timestamp(System.currentTimeMillis());
        String[] itemIds = {"1", "2"};
        String[] quantities = {"2", "1"};

        int billId = billDAO.saveBill(accountNumber, totalAmount, billDate, itemIds, quantities);
        assertTrue(billId > 0, "Bill ID should be greater than 0");
    }

    @Test
    @DisplayName("Save bill with null account number")
    void testSaveBillNullAccount() {
        double totalAmount = 500.0;
        Timestamp billDate = new Timestamp(System.currentTimeMillis());
        String[] itemIds = {"1"};
        String[] quantities = {"1"};

        int billId = billDAO.saveBill(null, totalAmount, billDate, itemIds, quantities);
        assertEquals(-1, billId, "Bill ID should be -1 for null account number");
    }

    @Test
    @DisplayName("Save bill with zero total amount")
    void testSaveBillZeroAmount() {
        String accountNumber = "C001";
        double totalAmount = 0.0;
        Timestamp billDate = new Timestamp(System.currentTimeMillis());
        String[] itemIds = {"1"};
        String[] quantities = {"1"};

        int billId = billDAO.saveBill(accountNumber, totalAmount, billDate, itemIds, quantities);
        assertTrue(billId > 0, "Bill should save even if total amount is zero");
    }
}
