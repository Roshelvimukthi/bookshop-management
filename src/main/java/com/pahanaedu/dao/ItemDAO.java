package com.pahanaedu.dao;

import com.pahanaedu.model.Item;
import com.pahanaedu.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    public boolean addItem(Item item) {
        String sql = "INSERT INTO items (name, price) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, item.getName());
            stmt.setDouble(2, item.getPrice());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateItem(Item item) {
        String sql = "UPDATE items SET name = ?, price = ? WHERE item_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, item.getName());
            stmt.setDouble(2, item.getPrice());
            stmt.setInt(3, item.getItemId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteItem(int itemId) {
        String sql = "DELETE FROM items WHERE item_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, itemId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String sql = "SELECT * FROM items";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                items.add(new Item(
                        rs.getInt("item_id"),
                        rs.getString("name"),
                        rs.getDouble("price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public Item getItemById(int itemId) {
        String sql = "SELECT * FROM items WHERE item_id = ?";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, itemId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Item(
                        rs.getInt("item_id"),
                        rs.getString("name"),
                        rs.getDouble("price")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}