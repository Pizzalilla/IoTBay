<%@ page import="java.util.*, com.mycompany.labs.model.CartItem" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Your Cart</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
    <style>
        .cart-item {
            display: flex;
            align-items: center;
            justify-content: space-between;
            background: #fff;
            padding: 15px;
            border-radius: 8px;
            box-shadow: 0 0 5px rgba(0,0,0,0.1);
            margin-bottom: 10px;
        }
        .cart-item img {
            width: 80px;
        }
        .cart-item .details {
            flex-grow: 1;
            margin-left: 15px;
        }
        .controls {
            margin-top: 30px;
        }
    </style>
    <script>
        function confirmSubmit(e) {
            const form = e.target;
            const clickedButton = document.activeElement;
            const action = clickedButton.name === "action" ? clickedButton.value : "";

            if (action === "submit") {
                const payment = document.getElementById("payment").value;
                if (!payment) {
                    alert("Please select a payment method before submitting.");
                    e.preventDefault();
                    return false;
                }
                if (!confirm("Submit this order?")) {
                    e.preventDefault();
                    return false;
                }
            }

            return true;
        }
    </script>
</head>
<body>
<nav class="navbar navbar-expand-lg bg-dark navbar-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="main.jsp">IoTBay</a>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item"><a class="nav-link" href="main.jsp">Home</a></li>
                <li class="nav-item"><a class="nav-link" href="OrderHistoryServlet">Orders</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container mt-4">
    <h2>Your Shopping Cart</h2>

<%
    List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cart");
    if (cartItems == null || cartItems.isEmpty()) {
        cartItems = new ArrayList<>();
        cartItems.add(new CartItem(0, null, 1, 2, new Date()));
        cartItems.add(new CartItem(0, null, 2, 1, new Date()));
        session.setAttribute("cart", cartItems);
    }

    Map<Integer, String> names = new HashMap<>();
    names.put(1, "Motion Sensor");
    names.put(2, "Temperature Sensor");

    Map<Integer, String> images = new HashMap<>();
    images.put(1, "motion.jpg");
    images.put(2, "temp.jpg");

    Map<Integer, Integer> stocks = new HashMap<>();
    stocks.put(1, 5);
    stocks.put(2, 2);
%>

<form method="post" action="CartController" onsubmit="return confirmSubmit(event);">
<% for (CartItem item : cartItems) {
    int deviceID = item.getDeviceID();
    int stock = stocks.getOrDefault(deviceID, 10);
%>
    <div class="cart-item">
        <img src="images/<%= images.getOrDefault(deviceID, "placeholder.jpg") %>" />
        <div class="details">
            <strong><%= names.getOrDefault(deviceID, "Unknown Device") %></strong><br>
            <small>Date Added: <%= item.getDateAdded() %></small><br>
            Quantity:
            <input type="number" name="quantity_<%= deviceID %>" value="<%= item.getQuantity() %>" min="1" max="<%= stock %>" />
            <input type="hidden" name="deviceID_<%= deviceID %>" value="<%= deviceID %>" />
        </div>
        <form method="post" action="CartController">
            <input type="hidden" name="action" value="remove" />
            <input type="hidden" name="deviceID" value="<%= deviceID %>" />
            <button type="submit" class="btn btn-danger">Remove</button>
        </form>
    </div>
<% } %>

    <div class="controls">
        <label for="payment">Payment Method:</label>
        <select name="paymentMethod" id="payment" class="form-select mb-3">
            <option value="" disabled selected>Select Payment</option>
            <option value="Visa">Visa</option>
            <option value="PayPal">PayPal</option>
        </select>
        <button type="submit" name="action" value="submit" class="btn btn-primary">Submit Order</button>
    </div>
</form>

<form method="post" action="CartController" class="mt-3">
    <input type="hidden" name="action" value="clear" />
    <button type="submit" class="btn btn-secondary">Clear Cart</button>
</form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
