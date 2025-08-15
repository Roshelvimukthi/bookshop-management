<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>Pahana Edu - Dashboard</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<% if (session.getAttribute("username") == null) { response.sendRedirect("login.jsp"); return; } %>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid">
    <a class="navbar-brand" href="#">Pahana Edu Bookshop</a>
    <div class="navbar-nav">
      <a class="nav-link" href="manageCustomers.jsp">Manage Customers</a>
      <a class="nav-link" href="manageItems.jsp">Manage Items</a>
      <a class="nav-link" href="generateBill.jsp">Generate Bill</a>
      <a class="nav-link" href="help.jsp">Help</a>
      <a class="nav-link" href="logout.jsp">Logout</a>
    </div>
  </div>
</nav>
<div class="container mt-5">
  <h2>Welcome, <%= session.getAttribute("username") %>!</h2>
  <p>Select an option from the navigation bar to manage customers, items, or generate bills.</p>
</div>
</body>
</html>