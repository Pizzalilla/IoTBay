<%@ page import="java.util.List" %>
<%@ page import="com.mycompany.labs.model.User" %>
<%@ page import="com.mycompany.labs.model.Log" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Logs</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%
    User user = (User) session.getAttribute("currentUser");
    if (user != null) {
        List<Log> logList = (List<Log>) session.getAttribute("logList");
%>

<nav class="navbar navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Login Logs</a>
        <span class="navbar-text text-white">
            <%= user.getFirstName() %> <%= user.getLastName() %> (<%= user.getEmail() != null ? user.getEmail() : "Account Deleted" %>)
        </span>
    </div>
</nav>

<div class="container mt-4">
    <div class="card">
        <div class="card-header">
            <h5 class="mb-0">Login History</h5>
        </div>
        <div class="card-body">

            <!-- Search by Date Form -->
            <form class="row g-3 mb-4" action="ReadLogServlet" method="get">
                <div class="col-auto">
                    <input type="date" name="logDate" class="form-control" required>
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-primary">Search by Date</button>
                </div>
            </form>

            <%
                if (logList != null && !logList.isEmpty()) {
            %>
            <table class="table table-bordered table-hover">
                <thead class="table-light">
                <tr>
                    <th>#</th>
                    <th>Login Time</th>
                    <th>Logout Time</th>
                </tr>
                </thead>
                <tbody>
                <%
                    int index = 1;
                    for (Log log : logList) {
                %>
                <tr>
                    <td><%= index++ %></td>
                    <td><%= log.getLoginTime() %></td>
                    <td><%= (log.getLogoutTime() != null) ? log.getLogoutTime() : "Still logged in or not recorded" %></td>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
            <% } else { %>
            <div class="alert alert-info">No login records found.</div>
            <% } %>

            <a href="main.jsp" class="btn btn-secondary mt-3">Back to Main</a>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<%
    } else {
        response.sendRedirect("login.jsp");
    }
%>
</body>
</html>
