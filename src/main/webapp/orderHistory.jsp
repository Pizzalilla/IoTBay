<%@ page import="java.util.*, com.mycompany.labs.model.Order" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Order History</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-4">
    <h2>Your Order History</h2>
    <form method="get" action="OrderHistoryServlet" class="row g-3 mb-4">
        <div class="col-md-4">
            <label for="orderID" class="form-label">Order ID</label>
            <input type="text" class="form-control" name="orderID" id="orderID" value="<%= request.getParameter("orderID") != null ? request.getParameter("orderID") : "" %>" />
        </div>
        <div class="col-md-4">
            <label for="orderDate" class="form-label">Order Date</label>
            <input type="date" class="form-control" name="orderDate" id="orderDate"
                   max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>"
                   value="<%= request.getParameter("orderDate") != null ? request.getParameter("orderDate") : "" %>" />
        </div>
        <div class="col-md-4 align-self-end">
            <button type="submit" class="btn btn-primary">Search</button>
        </div>
    </form>

<%
    List<Order> orders = new ArrayList<>();
    orders.add(new Order(101, 1, java.sql.Date.valueOf("2025-05-01"), "Pending"));
    orders.add(new Order(102, 1, java.sql.Date.valueOf("2025-05-02"), "Confirmed"));

    String searchID = request.getParameter("orderID");
    String searchDate = request.getParameter("orderDate");

    if (searchID != null || searchDate != null) {
        Iterator<Order> it = orders.iterator();
        while (it.hasNext()) {
            Order o = it.next();
            if ((searchID != null && !searchID.isEmpty() && o.getOrderID() != Integer.parseInt(searchID)) ||
                (searchDate != null && !searchDate.isEmpty() && !o.getOrderDate().toString().equals(searchDate))) {
                it.remove();
            }
        }
    }
%>
    <table class="table table-striped">
        <thead><tr><th>Order ID</th><th>Date</th><th>Status</th></tr></thead>
        <tbody>
        <% for (Order order : orders) { %>
            <tr>
                <td><%= order.getOrderID() %></td>
                <td><%= order.getOrderDate() %></td>
                <td><%= order.getStatus() %></td>
            </tr>
        <% } %>
        </tbody>
    </table>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
