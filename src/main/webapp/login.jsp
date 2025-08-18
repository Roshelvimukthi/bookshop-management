<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Pahana Edu - Login</title>
    <link rel="stylesheet" href="CSS/Style.css">
</head>
<body>
<div class="container">
    <h2>Pahana Edu Bookshop - Login</h2>
    <% if (request.getAttribute("error") != null) { %>
    <div class="Error Error_alert"><%= request.getAttribute("error") %></div>
    <% } %>
    <form action="login" method="post" class="">
        <div class="">
            <label for="username" class="form-label">Username</label>
            <input type="text" class="form-control" id="username" name="username" required>
        </div>
        <div class="">
            <label for="password" class="form-label">Password</label>
            <input type="password" class="form-control" id="password" name="password" required>
        </div>
        <button type="submit" class="btn-login">Login</button>
    </form>
</div>
</body>
</html>