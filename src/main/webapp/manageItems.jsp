<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pahanaedu.dao.ItemDAO, com.pahanaedu.model.Item, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Pahana Edu - Manage Items</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<% if (session.getAttribute("username") == null) { response.sendRedirect("login.jsp"); return; } %>
<div class="container mt-5">
    <h2>Manage Items</h2>
    <% if (request.getAttribute("message") != null) { %>
    <div class="alert alert-success"><%= request.getAttribute("message") %></div>
    <% } %>
    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
    <% } %>
    <h3>Add New Item</h3>
    <form action="item" method="post">
        <input type="hidden" name="action" value="add">
        <div class="mb-3">
            <label for="name" class="form-label">Item Name</label>
            <input type="text" class="form-control" id="name" name="name" required>
        </div>
        <div class="mb-3">
            <label for="price" class="form-label">Price ($)</label>
            <input type="number" step="0.01" class="form-control" id="price" name="price" required>
        </div>
        <button type="submit" class="btn btn-primary">Add Item</button>
    </form>

    <h3 class="mt-5">Item List</h3>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Item ID</th>
            <th>Name</th>
            <th>Price ($)</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <%
            ItemDAO itemDAO = new ItemDAO();
            List<Item> items = itemDAO.getAllItems();
            for (Item item : items) {
        %>
        <tr>
            <td><%= item.getItemId() %></td>
            <td><%= item.getName() %></td>
            <td><%= String.format("%.2f", item.getPrice()) %></td>
            <td>
                <form action="item" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="update">
                    <input type="hidden" name="itemId" value="<%= item.getItemId() %>">
                    <input type="text" name="name" value="<%= item.getName() %>" required>
                    <input type="number" step="0.01" name="price" value="<%= String.format("%.2f", item.getPrice()) %>" required>
                    <button type="submit" class="btn btn-sm btn-warning">Update</button>
                </form>
                <form action="item" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="itemId" value="<%= item.getItemId() %>">
                    <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure?')">Delete</button>
                </form>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <a href="dashboard.jsp" class="btn btn-secondary mt-3">Back to Dashboard</a>
</div>
</body>
</html>