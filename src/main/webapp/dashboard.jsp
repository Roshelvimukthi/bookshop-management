<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>Pahana Edu - Dashboard</title>
  <link rel="stylesheet" href="CSS/Style.css">
</head>
<body>
<% if (session.getAttribute("username") == null) { response.sendRedirect("login.jsp"); return; } %>
<nav class="navbar">
  <div class="dashboard_container-fluid">
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
<div class="dashboard_container">
  <h2>Welcome, <%= session.getAttribute("username") %>!</h2>
  <p>Select an option from the navigation bar to manage customers, items, or generate bills.</p>
</div>
</body>
</html>