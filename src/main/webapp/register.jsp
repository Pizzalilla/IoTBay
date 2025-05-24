<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registration Page</title>
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

<div class="container-md mt-4">
    <h2 class="mb-4 mt-3">User Registration</h2>

    <% String error = (String) request.getAttribute("error"); %>
    <% if (error != null) { %>
    <div class="alert alert-danger" role="alert"><%= error %></div>
    <% } %>

    <form action="register" method="post" class="row g-3">
        <div class="col-md-6">
            <label for="firstName" class="form-label">First Name</label>
            <input type="text" class="form-control" name="firstName" value="<%= request.getAttribute("firstName") != null ? request.getAttribute("firstName") : "" %>" required>
        </div>
        <div class="col-md-6">
            <label for="lastName" class="form-label">Last Name</label>
            <input type="text" class="form-control" name="lastName" value="<%= request.getAttribute("lastName") != null ? request.getAttribute("lastName") : "" %>" required>
        </div>
        <div class="col-md-6">
            <label for="email" class="form-label">Email</label>
            <input type="email" class="form-control" name="email" value="<%= request.getAttribute("email") != null ? request.getAttribute("email") : "" %>" required>
        </div>
        <div class="col-md-6">
            <label for="password" class="form-label">Password</label>
            <input type="password" class="form-control" name="password" value="<%= request.getAttribute("password") != null ? request.getAttribute("password") : "" %>" required>
        </div>
        <div class="col-md-6">
            <label for="mobile" class="form-label">Mobile</label>
            <input type="text" class="form-control" name="mobile" value="<%= request.getAttribute("mobile") != null ? request.getAttribute("mobile") : "" %>" required>
        </div>
        <div class="col-md-6">
            <label for="gender" class="form-label">Gender</label>
            <select class="form-control" name="gender" required>
                <option value="">Select</option>
                <option value="Male" <%= "Male".equals(request.getAttribute("gender")) ? "selected" : "" %>>Male</option>
                <option value="Female" <%= "Female".equals(request.getAttribute("gender")) ? "selected" : "" %>>Female</option>
                <option value="Non-binary" <%= "Non-binary".equals(request.getAttribute("gender")) ? "selected" : "" %>>Non-binary</option>
            </select>
        </div>
        <div class="col-12">
            <label for="address" class="form-label">Address</label>
            <input type="text" class="form-control" name="address" value="<%= request.getAttribute("address") != null ? request.getAttribute("address") : "" %>" required>
        </div>
        <div class="col-md-6">
            <label for="type" class="form-label">User Type</label>
            <select class="form-control" name="type" required>
                <option value="">Select</option>
                <option value="customer" <%= "customer".equals(request.getAttribute("type")) ? "selected" : "" %>>Customer</option>
                <option value="staff" <%= "staff".equals(request.getAttribute("type")) ? "selected" : "" %>>Staff</option>
            </select>
        </div>
        <div class="col-md-6">
            <label for="position" class="form-label">Position (for staff)</label>
            <input type="text" class="form-control" name="position" value="<%= request.getAttribute("position") != null ? request.getAttribute("position") : "" %>">
        </div>
        <div class="col-12">
            <button type="submit" class="btn btn-primary">Sign Up</button>
        </div>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
