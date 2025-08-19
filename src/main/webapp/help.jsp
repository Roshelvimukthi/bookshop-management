<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Pahana Edu - Help</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<% if (session.getAttribute("username") == null) { response.sendRedirect("login.jsp"); return; } %>
<div class="container mt-5">
    <h2>Help - Pahana Edu Bookshop System</h2>
    <div class="card">
        <div class="card-body">
            <h3>System Usage Guidelines</h3>
            <p>Welcome to the Pahana Edu Bookshop Management System. Follow these steps to use the system effectively:</p>
            <ul>
                <li><strong>Login</strong>: Use your username and password to access the system.</li>
                <li><strong>Dashboard</strong>: Navigate to different sections using the menu bar.</li>
                <li><strong>Manage Customers</strong>: Add new customers or update existing customer details.</li>
                <li><strong>Manage Items</strong>: Add, update, or delete book items and their prices.</li>
                <li><strong>Generate Bill</strong>: Select a customer and item, enter units consumed, and generate a bill.</li>
                <li><strong>Logout</strong>: Click the logout link to securely exit the system.</li>
            </ul>
            <p>For further assistance, contact the system administrator.</p>
            <a href="dashboard.jsp" class="btn btn-secondary mt-3">Back to Dashboard</a>
        </div>
    </div>
</div>
</body>
</html>