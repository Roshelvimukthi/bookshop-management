package com.pahanaedu.dao;

import com.pahanaedu.model.Customer;
import com.pahanaedu.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (account_number, name, address, phone) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getAccountNumber());
            stmt.setString(2, customer.getName());
            stmt.setString(3, customer.getAddress());
            stmt.setString(4, customer.getPhone());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE customers SET name = ?, address = ?, phone = ? WHERE account_number = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getAddress());
            stmt.setString(3, customer.getPhone());
            stmt.setString(4, customer.getAccountNumber());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Customer getCustomerByAccountNumber(String accountNumber) {
        String sql = "SELECT * FROM customers WHERE account_number = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Customer(
                        rs.getString("account_number"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT * FROM customers";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                customers.add(new Customer(
                        rs.getString("account_number"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getString("phone")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public boolean deleteCustomer(String accountNumber) {
        String sql = "DELETE FROM customers WHERE account_number = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountNumber);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}