<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="com.mycompany.labs.model.User" %>

<%
String email = request.getParameter("email");
String password = request.getParameter("password");

if (email != null && password != null) {
    User user = new User();
    user.setEmail(email);

    if (user.getEmail().equals("customer@test.com")) {
        user.setStaff(false);
        user.setFirstName("Kai");
        user.setLastName("Cust");

        // ✅ Assume customer@test.com has customerID = 1
        session.setAttribute("customerID", 1);
        session.setAttribute("currentUser", user);
        application.setAttribute(email, user);
        response.sendRedirect("main.jsp");

    } else if (user.getEmail().equals("staff@test.com")) {
        user.setStaff(true);
        user.setFirstName("Eric");
        user.setLastName("Fang");

        // Optional: staffID logic (not needed for cart)
        session.setAttribute("currentUser", user);
        application.setAttribute(email, user);
        response.sendRedirect("home");
    } else {
        request.setAttribute("loginError", "Invalid email or password.");
    }
}
%>


<%@ page import="java.sql.*" %>
<%@ page import="com.mycompany.labs.DAO.DB" %>

<%
    try {
        Connection conn = DB.getConnection();
        out.println("<p style='color:green;'>✅ Connected to database!</p>");
    } catch (Exception e) {
        out.println("<p style='color:red;'>❌ DB connection failed:</p>");
        e.printStackTrace(new java.io.PrintWriter(out));

    }
%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    </head>
    <body>
        <nav class="navbar navbar-expand-lg bg-dark navbar-dark">
            <div class="container-fluid">
                <a class="navbar-brand" href="/">IoTBay</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="/">Home</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="/">Orders</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="/">Shipments</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="/">Payments</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="/">Users</a>
                        </li>
                    </ul>
                    <form class="d-flex" role="search">
                        <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                        <button class="btn btn-outline-success" type="submit">Search</button>
                    </form>
                </div>
            </div>
        </nav>

        <div class="container">
            <h1 class="mt-5 mb-4">Login</h1>
            <form method="post">
                <input type="hidden" name="type" value="login">
                <% if (request.getAttribute("loginError") != null) { %>
                    <div class="alert alert-danger"><%= request.getAttribute("loginError") %></div>
                <% } %>
                <div class="row mb-3">
                    <label for="email" class="col-sm-2 col-form-label">Email:</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="email" name="email" required>
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
                </div>
            </form>
        </div>
    </body>
</html>
