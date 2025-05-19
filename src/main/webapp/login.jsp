<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.mycompany.labs.model.User" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg bg-dark navbar-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">IoTBay</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item"><a class="nav-link active" href="/">Home</a></li>
                <li class="nav-item"><a class="nav-link active" href="/">Orders</a></li>
                <li class="nav-item"><a class="nav-link active" href="/">Shipments</a></li>
                <li class="nav-item"><a class="nav-link active" href="/">Payments</a></li>
                <li class="nav-item"><a class="nav-link active" href="/">Users</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container">
    <h1 class="mt-5 mb-4">Login</h1>

    <form action="login" method="post">
        <% if (request.getAttribute("loginError") != null) { %>
        <div class="alert alert-danger"><%= request.getAttribute("loginError") %></div>
        <% } %>

        <div class="row mb-3">
            <label for="email" class="col-sm-2 col-form-label">Email (Username):</label>
            <div class="col-sm-10">
                <input type="text" class="form-control" id="email" name="email"
                       value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>" required>
            </div>
        </div>

        <div class="row mb-3">
            <label for="password" class="col-sm-2 col-form-label">Password:</label>
            <div class="col-sm-10">
                <input type="password" class="form-control" id="password" name="password" required>
            </div>
        </div>

        <div class="d-flex gap-2">
            <button type="submit" class="btn btn-primary">Login</button>
            <a href="register.jsp" class="btn btn-outline-secondary">Sign Up</a>
            <!-- Admin Login Info -->
            <button type="submit" class="btn btn-warning" name="adminLogin" value="true">Admin Login</button>
        </div>
    </form>
</div>
</body>
</html>
