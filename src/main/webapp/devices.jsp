<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, com.mycompany.labs.model.Device" %>
<html>
<head>
    <title>All Devices</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-4Q6Gf2aSP4eDXB8Miphtr37CMZZQ5oXLH2yaXMJ2w8e2ZtHTl7GptT4jmndRuHDT" crossorigin="anonymous">
</head>
<body>
<nav class="navbar navbar-expand-lg bg-dark navbar-dark">
    <%@page import="com.mycompany.labs.model.User"%>
    <%@ page import="com.mycompany.labs.model.Device" %>
    <%@ page import="java.util.List" %>
    <%
        User currentUser = (User) request.getSession().getAttribute("currentUser");
        if (currentUser == null) {
            response.sendRedirect("login.jsp");
        }
    %>
    <div class="container-fluid">
        <a class="navbar-brand" href="./home">IoTBay</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="./home">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="devices">Devices</a>
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
                <input class="form-control me-2" type="search" placeholder="Search name or type" name="keyword">
                <button class="btn btn-outline-success" type="submit">Search</button>
            </form>

            <div class="ms-5 text-white">Welcome <%= currentUser.getFirstName()%> <%= currentUser.getLastName()%></div>
            <div class="ms-5 text-white"><a href="logout.jsp">Logout</a></div>
        </div>
    </div>
</nav>
<div class="container mt-3">
    <h2>
        Device List
        <a class="btn btn-success" href="addDevice"> + Device </a>
    </h2>
    <%
        List<Device> deviceList = (List<Device>) request.getAttribute("devices");
    %>
    <table class="table table-striped">
        <th>ID</th>
        <th>Name</th>
        <th>Price ($)</th>
        <th>Type</th>
        <th>Stock</th>
        <th>Image</th>
        <th>Actions</th>
        <%
            if (deviceList != null && !deviceList.isEmpty()) {
                for (Device d : deviceList) {
        %>
        <tr>
            <td><%= d.getDeviceId() %>
            </td>
            <td><%= d.getDeviceName() %>
            </td>
            <td><%= String.format("%.2f", d.getPrice()) %>
            </td>
            <td><%= d.getType() %>
            </td>
            <td><%= d.getStockQty() %>
            </td>
            <td><img src="images/<%= d.getImageName() %>" height="100"/></td>
            <td><a class="btn btn-danger" href="deleteDevice?id=<%= d.getDeviceId() %>">Delete</a> <a
                    class="btn btn-warning" href="updateDevice?id=<%= d.getDeviceId() %>">Update</a></td>
        </tr>
        <%
            }
        } else {
        %>
        <tr>
            <td colspan="6" style="text-align:center;">No devices found.</td>
        </tr>
        <% } %>
    </table>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.6/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-j1CDi7MgGQ12Z7Qab0qlWQ/Qqz24Gc6BM0thvEMVjHnfYGF0rmFCozFSxQBxwHKO"
        crossorigin="anonymous"></script>
</body>
</html>
