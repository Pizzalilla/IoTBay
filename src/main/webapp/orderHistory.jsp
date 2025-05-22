<%@ page import="java.util.*, com.mycompany.labs.model.Order, com.mycompany.labs.model.OrderProduct" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Order History</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg bg-dark navbar-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="main.jsp">IoTBay</a>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="/IoTBay_main/devices.jsp">Devices</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="/IoTBay_main/CartController">Cart</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="/">Shipments</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="/IoTBay_main/payment.jsp">Payments</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="/">Users</a>
                        </li>
                    </ul>
        </div>
    </div>
</nav>

<div class="container mt-4">
    <h2>Your Order History</h2>

    <form method="get" action="OrderHistoryServlet" class="row g-3 mb-4">
        <div class="col-md-4">
            <label for="orderID" class="form-label">Order ID</label>
            <input type="text" class="form-control" name="orderID" id="orderID"
                   value="${param.orderID != null ? param.orderID : ''}" />
        </div>
        <div class="col-md-4">
            <label for="orderDate" class="form-label">Order Date</label>
            <input type="date" class="form-control" name="orderDate" id="orderDate"
                   max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>"
                   value="${param.orderDate != null ? param.orderDate : ''}" />
        </div>
        <div class="col-md-4 align-self-end">
            <button type="submit" class="btn btn-primary">Search</button>
        </div>
    </form>

    <c:choose>
        <c:when test="${empty orders}">
            <p>No orders found.</p>
        </c:when>
        <c:otherwise>
            <c:forEach var="order" items="${orders}">
                <div class="card mb-4">
                    <div class="card-header">
                        <strong>Order #${order.orderID}</strong> — Date: ${order.orderDate} — Status: ${order.status}
                    </div>
                    <div class="card-body">
                        <table class="table table-sm">
                            <thead>
                                <tr>
                                    <th>Device Name</th>
                                    <th>Quantity</th>
                                    <th>Unit Price ($)</th>
                                    <th>Subtotal ($)</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="total" value="0" scope="page" />
                                <c:forEach var="item" items="${orderDetailsMap[order.orderID]}">
                                    <tr>
                                        <td>${item.deviceName}</td>
                                        <td>${item.quantity}</td>
                                        <td>${item.unitPrice}</td>
                                        <td>${item.subtotal}</td>
                                    </tr>
                                    <c:set var="total" value="${total + item.subtotal}" scope="page" />
                                </c:forEach>
                            </tbody>
                        </table>
                        <h5 class="text-end">Total: $<c:out value="${total}" /></h5>
                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
