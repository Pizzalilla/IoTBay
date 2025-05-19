<%@ page import="java.util.List" %>
<%@ page import="com.mycompany.labs.model.User" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%
    Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
    if (isAdmin == null || !isAdmin) {
        response.sendRedirect("login.jsp");
        return;
    }

    List<User> userList = (List<User>) request.getAttribute("userList");
    String keyword = request.getParameter("keyword") != null ? request.getParameter("keyword") : "";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin - User Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h2 class="mb-4">User Management</h2>

    <!-- Search Form -->
    <form action="AdminUserServlet" method="get" class="row g-3 mb-4">
        <div class="col-md-6">
            <input type="text" name="keyword" class="form-control" placeholder="Search by full name or mobile"
                   value="<%= keyword %>">
        </div>
        <div class="col-auto">
            <button type="submit" class="btn btn-primary">Search</button>
            <a href="AdminUserServlet" class="btn btn-secondary">Reset</a>
        </div>
    </form>

    <!-- Create User -->
    <div class="mb-3">
        <a href="register.jsp" class="btn btn-success">Create New User</a>
    </div>

    <!-- User Table -->
    <%
        if (userList != null && !userList.isEmpty()) {
    %>
    <%= userList.get(0).isActive() %>
    <table class="table table-bordered table-hover">
        <thead class="table-light">
        <tr>
            <th>#</th>
            <th>Full Name</th>
            <th>Email</th>
            <th>Mobile</th>
            <th>Type</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            int index = 1;
            for (User user : userList) {
        %>
        <tr>
            <td><%= index++ %></td>
            <td><%= user.getFirstName() %> <%= user.getLastName() %></td>
            <td><%= user.getEmail() %></td>
            <td><%= user.getMobile() %></td>
            <td><%= user.getType() %></td>
            <td><%= user.isActive() ? "Active" : "Inactive" %></td>
            <td>
                <form action="AdminUserServlet" method="post" class="d-inline">
                    <input type="hidden" name="userId" value="<%= user.getUserID() %>">
                    <input type="hidden" name="action" value="toggleStatus">
                    <button type="submit" class="btn btn-sm <%= user.isActive() ? "btn-warning" : "btn-success" %>">
                        <%= user.isActive() ? "Deactivate" : "Activate" %>
                    </button>
                </form>
                <form action="AdminUserServlet" method="post" class="d-inline" onsubmit="return confirm('Are you sure?');">
                    <input type="hidden" name="userId" value="<%= user.getUserID() %>">
                    <input type="hidden" name="action" value="delete">
                    <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                </form>
            </td>
        </tr>
        <%
            }
        %>
        </tbody>
    </table>
    <% } else { %>
    <div class="alert alert-info">No users found.</div>
    <% } %>

    <a href="logout.jsp" class="btn btn-outline-dark mt-3">Logout</a>
</div>
</body>
</html>
