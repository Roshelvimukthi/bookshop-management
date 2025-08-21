package com.pahanaedu.servlet;

import com.pahanaedu.dao.CustomerDAO;
import com.pahanaedu.model.Customer;
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
@WebServlet("/customer")
public class CustomerServlet extends HttpServlet {
    private CustomerDAO customerDAO = new CustomerDAO();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        request.getRequestDispatcher("manageCustomers.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            if ("add".equals(action)) {
                Customer customer = new Customer(
                        request.getParameter("accountNumber"),
                        request.getParameter("name"),
                        request.getParameter("address"),
                        request.getParameter("phone")
                );
                if (customerDAO.addCustomer(customer)) {
                    request.setAttribute("message", "Customer added successfully");
                } else {
                    request.setAttribute("error", "Failed to add customer already exist ");
                }
            } else if ("update".equals(action)) {
                Customer customer = new Customer(
                        request.getParameter("accountNumber"),
                        request.getParameter("name"),
                        request.getParameter("address"),
                        request.getParameter("phone")
                );
                if (customerDAO.updateCustomer(customer)) {
                    request.setAttribute("message", "Customer updated successfully");
                } else {
                    request.setAttribute("error", "Failed to update customer");
                }
            } else if ("delete".equals(action)) {
                String accountNumber = request.getParameter("accountNumber");
                if (customerDAO.deleteCustomer(accountNumber)) {
                    request.setAttribute("message", "Customer deleted successfully");
                } else {
                    request.setAttribute("error", "Failed to delete customer");
                }
            }
        } catch (Exception e) {
            request.setAttribute("error", "Invalid input");
        }
        request.getRequestDispatcher("manageCustomers.jsp").forward(request, response);
    }
}