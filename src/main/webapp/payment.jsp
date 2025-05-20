<%-- payment.jsp --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="com.mycompany.labs.DAO.PaymentDAO"%>
<%@page import="com.mycompany.labs.model.Payment"%>
<%@ page import="java.util.List" %>
<%
    if(request.getAttribute("payments") == null) {
        List<Payment> payments = new PaymentDAO().getAllPayments();
        request.setAttribute("payments", payments);
    }
%>
<html>
<head>
    <title>Payment Management</title>
    <style>
        .container { max-width: 1000px; margin: 20px auto; padding: 20px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .form-section { margin-bottom: 30px; background: #f8f9fa; padding: 20px; }
        .search-section { margin-bottom: 20px; }
        .message { color: green; margin: 10px 0; }
        .error { color: red; }
    </style>
</head>
<body>
<div class="container">
    <h2>Payment Management</h2>

    <%-- Success/Error Messages --%>
    <c:if test="${not empty message}">
        <div class="message">${message}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>

    <%-- Search Form --%>
    <div class="search-section">
        <form action="${pageContext.request.contextPath}/PaymentSearchServlet" method="get">
            <input type="text" name="paymentId" placeholder="Payment ID">
            <input type="date" name="paymentDate">
            <button type="submit">Search</button>
        </form>
    </div>

    <%-- Create Payment Form --%>
    <div class="form-section">
        <h3>Create Payment</h3>
        <form action="${pageContext.request.contextPath}/PaymentCreateServlet" method="post">
            Order ID: <input type="number" name="orderId" required><br>
            Payment Method:
            <select name="paymentMethod" required>
                <option value="Credit Card">Credit Card</option>
                <option value="PayPal">PayPal</option>
            </select><br>
            Amount: <input type="number" step="0.01" name="amount" required><br>
            Date: <input type="date" name="paymentDate" required><br>
            <button type="submit">Create Payment</button>
        </form>
    </div>

    <%-- Payment List Table --%>
    <h3>Payment History</h3>
    <table>
        <tr>
            <th>Payment ID</th>
            <th>Order ID</th>
            <th>Method</th>
            <th>Amount</th>
            <th>Date</th>
            <th>Status</th>
            <th>Actions</th>
        </tr>
        <c:forEach items="${payments}" var="payment">
            <tr>
                <td>${payment.paymentID}</td>
                <td>${payment.orderID}</td>
                <td>${payment.paymentMethod}</td>
                <td><fmt:formatNumber value="${payment.totalPrice}" type="currency"/></td>
                <td><fmt:formatDate value="${payment.paymentDate}" pattern="yyyy-MM-dd"/></td>
                <td>${payment.status}</td>
                <td>
                    <form action="${pageContext.request.contextPath}/PaymentEditServlet" method="get" style="display:inline;">
                        <input type="hidden" name="paymentId" value="${payment.paymentID}">
                        <button type="submit">Edit</button>
                    </form>
                    <form action="${pageContext.request.contextPath}/PaymentDeleteServlet" method="post" style="display:inline;">
                        <input type="hidden" name="paymentId" value="${payment.paymentID}">
                        <button type="submit" onclick="return confirm('Are you sure?')">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>
</body>
</html>