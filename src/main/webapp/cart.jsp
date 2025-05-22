<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.mycompany.labs.model.CartItem" %>
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
        .cart-item .details {
            flex-grow: 1;
            margin-left: 15px;
        }
        .controls {
            margin-top: 30px;
        }
        .inline-form {
            display: flex;
            align-items: center;
            gap: 10px;
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
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="/IoTBay_main/devices.jsp">Devices</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="/IoTBay_main/OrderHistoryServlet">Order History</a>
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
    <h2>Your Shopping Cart</h2>

    <c:choose>
        <c:when test="${empty cartItems}">
            <p>Your cart is currently empty.</p>
        </c:when>
        <c:otherwise>
            <c:forEach var="item" items="${cartItems}">
                <div class="cart-item">
                    <div class="details">
                        <strong>${item.deviceName}</strong><br>
                        <small>Date Added: ${item.dateAdded}</small><br>
                        Unit Price: $${item.unitPrice} <br>

                        <form method="post" action="CartController" class="inline-form">
                            <input type="hidden" name="action" value="update" />
                            <input type="hidden" name="cartItemID" value="${item.cartItemID}" />
                            <input type="hidden" name="deviceID" value="${item.deviceID}" />
                            Quantity:
                            <input type="number" name="quantity" value="${item.quantity}" min="1" max="${item.maxStock}" class="form-control" style="width: 80px;" />
                            <button type="submit" class="btn btn-sm btn-success">Update</button>
                        </form>

                        Subtotal: $<c:out value="${item.unitPrice * item.quantity}" />
                    </div>

                    <form method="post" action="CartController">
                        <input type="hidden" name="action" value="remove" />
                        <input type="hidden" name="cartItemID" value="${item.cartItemID}" />
                        <button type="submit" class="btn btn-danger">Remove</button>
                    </form>
                </div>
            </c:forEach>

            <!-- Submit form -->
            <form method="post" action="CartController" onsubmit="return confirmSubmit(event);">
                <div class="controls">
                    <label for="payment">Payment Method:</label>
                    <select name="paymentMethod" id="payment" class="form-select mb-3">
                        <option value="" disabled selected>Select Payment</option>
                        <option value="Visa">Visa</option>
                        <option value="PayPal">PayPal</option>
                    </select>

                    <h4>Total: $<c:out value="${totalPrice}" /></h4>
                    <button type="submit" name="action" value="submit" class="btn btn-primary">Submit Order</button>
                </div>
            </form>

            <!-- Clear cart form -->
            <form method="post" action="CartController" class="mt-3">
                <input type="hidden" name="action" value="clear" />
                <button type="submit" class="btn btn-secondary">Clear Cart</button>
            </form>
        </c:otherwise>
    </c:choose>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
