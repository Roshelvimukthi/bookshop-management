<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pahanaedu.dao.CustomerDAO, com.pahanaedu.model.Customer, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
  <title>Pahana Edu - Manage Customers</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<% if (session.getAttribute("username") == null) { response.sendRedirect("login.jsp"); return; } %>
<div class="container mt-5">
  <h2>Manage Customers</h2>
  <% if (request.getAttribute("message") != null) { %>
  <div class="alert alert-success"><%= request.getAttribute("message") %></div>
  <% } %>
  <% if (request.getAttribute("error") != null) { %>
  <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
  <% } %>
  <h3>Add New Customer</h3>
  <form action="customer" method="post">
    <input type="hidden" name="action" value="add">
    <div class="mb-3">
      <label for="accountNumber" class="form-label">Account Number</label>
      <input type="text" class="form-control" id="accountNumber" name="accountNumber" required>
    </div>
    <div class="mb-3">
      <label for="name" class="form-label">Name</label>
      <input type="text" class="form-control" id="name" name="name" required>
    </div>
    <div class="mb-3">
      <label for="address" class="form-label">Address</label>
      <input type="text" class="form-control" id="address" name="address" required>
    </div>
    <div class="mb-3">
      <label for="phone" class="form-label">Phone</label>
      <input type="text" class="form-control" id="phone" name="phone" required>
    </div>
    <button type="submit" class="btn btn-primary">Add Customer</button>
  </form>

  <h3 class="mt-5">Customer List</h3>
  <table class="table table-striped">
    <thead>
    <tr>
      <th>Account Number</th>
      <th>Name</th>
      <th>Address</th>
      <th>Phone</th>
      <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <%
      CustomerDAO customerDAO = new CustomerDAO();
      List<Customer> customers = customerDAO.getAllCustomers();
      for (Customer customer : customers) {
    %>
    <tr>
      <td><%= customer.getAccountNumber() %></td>
      <td><%= customer.getName() %></td>
      <td><%= customer.getAddress() %></td>
      <td><%= customer.getPhone() %></td>
      <td>
        <form action="customer" method="post" style="display:inline;">
          <input type="hidden" name="action" value="update">
          <input type="hidden" name="accountNumber" value="<%= customer.getAccountNumber() %>">
          <input type="text" name="name" value="<%= customer.getName() %>" required>
          <input type="text" name="address" value="<%= customer.getAddress() %>" required>
          <input type="text" name="phone" value="<%= customer.getPhone() %>" required>
          <button type="submit" class="btn btn-sm btn-warning">Update</button>
        </form>
        <form action="customer" method="post" style="display:inline;">
          <input type="hidden" name="action" value="delete">
          <input type="hidden" name="accountNumber" value="<%= customer.getAccountNumber() %>">
          <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to delete this customer?')">Delete</button>
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