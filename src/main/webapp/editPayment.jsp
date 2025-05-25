<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.mycompany.labs.model.Payment" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    Payment payment = (Payment) request.getAttribute("payment");
    String error = (String) session.getAttribute("error");
    session.removeAttribute("error");

    if (payment == null) {
        response.sendRedirect("payment.jsp");
        return;
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Payment</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="p-4">
<div class="container">
    <h2>Edit Payment</h2>

    <% if (error != null) { %>
    <div class="alert alert-danger"><%= error %></div>
    <% } %>

    <form action="PaymentEditServlet" method="post" class="row g-3">
        <input type="hidden" name="paymentId" value="<%= payment.getPaymentID() %>">

        <div class="col-md-3">
            <label class="form-label">Order ID</label>
            <input type="number" name="orderId" class="form-control" value="<%= payment.getOrderID() %>" required>
        </div>

        <div class="col-md-3">
            <label class="form-label">Payment Method</label>
            <input type="text" name="paymentMethod" class="form-control" value="<%= payment.getPaymentMethod() %>" required>
        </div>

        <div class="col-md-3">
            <label class="form-label">Amount</label>
            <input type="number" step="0.01" name="amount" class="form-control" value="<%= payment.getTotalPrice() %>" required>
        </div>

        <div class="col-md-3">
            <label class="form-label">Payment Date</label>
            <input type="date" name="paymentDate" class="form-control" value="<%= sdf.format(payment.getPaymentDate()) %>" required>
        </div>

        <div class="col-md-3">
            <label class="form-label">Status</label>
            <select name="status" class="form-select" required>
                <option value="Pending" <%= "Pending".equals(payment.getStatus()) ? "selected" : "" %>>Pending</option>
                <option value="Completed" <%= "Completed".equals(payment.getStatus()) ? "selected" : "" %>>Completed</option>
                <option value="Failed" <%= "Failed".equals(payment.getStatus()) ? "selected" : "" %>>Failed</option>
            </select>
        </div>

        <div class="col-12">
            <button type="submit" class="btn btn-primary">Update</button>
            <a href="payment.jsp" class="btn btn-secondary">Cancel</a>
        </div>
    </form>
</div>
</body>
</html>
