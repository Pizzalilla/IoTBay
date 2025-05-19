<%@page import="com.mycompany.labs.model.User"%>
<%
    User currentUser = (User) request.getSession().getAttribute("currentUser");
    if (currentUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Main Page</title>
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

            <span class="text-white me-3">
                Welcome, <%= currentUser.getFirstName() %> <%= currentUser.getLastName() %>!
            </span>
            <a href="../../../../../../Documents/GitHub/IoTBay/src/main/webapp/logout.jsp" class="btn btn-outline-light">Logout</a>
        </div>
    </div>
</nav>

<div class="container mt-4">
    <h3>User Info</h3>
    <p><strong>Full Name:</strong> <%= currentUser.getFirstName() %> <%= currentUser.getLastName() %></p>
    <p><strong>Email:</strong> <%= currentUser.getEmail() %></p>
    <p><strong>Mobile:</strong> <%= currentUser.getMobile() != null ? currentUser.getMobile() : "N/A" %></p>
    <p><strong>Gender:</strong> <%= currentUser.getGender() != null ? currentUser.getGender() : "N/A" %></p>
    <p><strong>Address:</strong> <%= currentUser.getAddress() %></p>
    <p><strong>User Type:</strong> <%= currentUser.getType() %></p>
    <% if ("staff".equals(currentUser.getType())) { %>
    <p><strong>Position:</strong> <%= currentUser.getPosition() %></p>
    <% } %>

    <div class="mt-4 d-flex gap-3">
        <a href="../../../../../../Documents/GitHub/IoTBay/src/main/webapp/editProfile.jsp" class="btn btn-primary">Edit Profile</a>
        <a href="ReadLogServlet" class="btn btn-secondary">View Activity Log</a>
        <form action="DeleteAccountServlet" method="post" onsubmit="return confirm('Are you sure you want to delete your account? This cannot be undone.');">
            <button type="submit" class="btn btn-danger">Delete Account</button>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
