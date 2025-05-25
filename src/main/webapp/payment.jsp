<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.mycompany.labs.model.Payment" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    List<Payment> payments = (List<Payment>) request.getAttribute("payments");
    String message = (String) session.getAttribute("message");
    String error = (String) session.getAttribute("error");
    session.removeAttribute("message");
    session.removeAttribute("error");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Payment Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="p-4">
<div class="container">
    <h2>Payment Management</h2>

    <% if (message != null) { %>
    <div class="alert alert-success"><%= message %></div>
    <% } %>
    <% if (error != null) { %>
    <div class="alert alert-danger"><%= error %></div>
    <% } %>

    <!-- Search Form -->
    <form action="PaymentSearchServlet" method="get" class="row g-3 my-3">
        <div class="col-md-3">
            <input type="number" name="paymentId" class="form-control" placeholder="Payment ID">
        </div>
        <div class="col-md-3">
            <input type="date" name="paymentDate" class="form-control">
        </div>
        <div class="col-md-3">
            <button type="submit" class="btn btn-primary">Search</button>
        </div>
    </form>

    <!-- Create Form -->
    <form action="PaymentCreateServlet" method="post" class="row g-3 border p-3 mb-4">
        <h4>Add Payment</h4>
        <div class="col-md-2">
            <input type="number" name="orderId" class="form-control" placeholder="Order ID" required>
        </div>
        <div class="col-md-2">
            <input type="text" name="paymentMethod" class="form-control" placeholder="Method" required>
        </div>
        <div class="col-md-2">
            <input type="number" name="amount" step="0.01" class="form-control" placeholder="Amount" required>
        </div>
        <div class="col-md-2">
            <input type="date" name="paymentDate" class="form-control" required>
        </div>
        <div class="col-md-2">
            <button type="submit" class="btn btn-success">Add</button>
        </div>
    </form>

    <!-- Payment List Table -->
    <table class="table table-bordered table-hover">
        <thead>
        <tr>
            <th>ID</th>
            <th>OrderID</th>
            <th>Method</th>
            <th>Date</th>
            <th>Amount</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <%
            if (payments != null && !payments.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                for (Payment p : payments) {
        %>
        <tr>
            <td><%= p.getPaymentID() %></td>
            <td><%= p.getOrderID() %></td>
            <td><%= p.getPaymentMethod() %></td>
            <td><%= sdf.format(p.getPaymentDate()) %></td>
            <td>$<%= p.getTotalPrice() %></td>
            <td><%= p.getStatus() %></td>
            <td>
                <form action="PaymentDeleteServlet" method="post" style="display:inline-block;">
                    <input type="hidden" name="paymentId" value="<%= p.getPaymentID() %>">
                    <button class="btn btn-sm btn-danger" onclick="return confirm('Delete this payment?')">Delete</button>
                </form>
                <a href="PaymentEditServlet?paymentId=<%= p.getPaymentID() %>" class="btn btn-sm btn-secondary">Edit</a>
            </td>
        </tr>
        <%
            }
        } else {
        %>
        <tr><td colspan="7">No payments found.</td></tr>
        <% } %>
        </tbody>
    </table>
</div>
</body>
</html>
