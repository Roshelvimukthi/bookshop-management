package com.pahanaedu.servlet;

import com.pahanaedu.dao.ItemDAO;
import com.pahanaedu.model.Item;


import com.pahanaedu.util.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
@WebServlet("/item")
public class ItemServlet extends HttpServlet {
    private ItemDAO itemDAO = new ItemDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                Item item = new Item(
                        0,
                        request.getParameter("name"),
                        Double.parseDouble(request.getParameter("price"))
                );
                if (itemDAO.addItem(item)) {
                    request.setAttribute("message", "Item added successfully");
                } else {
                    request.setAttribute("error", "Failed to add item");
                }
            } else if ("update".equals(action)) {
                Item item = new Item(
                        Integer.parseInt(request.getParameter("itemId")),
                        request.getParameter("name"),
                        Double.parseDouble(request.getParameter("price"))
                );
                if (itemDAO.updateItem(item)) {
                    request.setAttribute("message", "Item updated successfully");
                } else {
                    request.setAttribute("error", "Failed to update item");
                }
            } else if ("delete".equals(action)) {
                int itemId = Integer.parseInt(request.getParameter("itemId"));
                if (itemDAO.deleteItem(itemId)) {
                    request.setAttribute("message", "Item deleted successfully");
                } else {
                    request.setAttribute("error", "Failed to delete item");
                }
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid input for price or item ID");
        }
        request.getRequestDispatcher("manageItems.jsp").forward(request, response);
    }
}