<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Edit Payment</title>
    <style>/* 复用payment.jsp样式 */</style>
</head>
<body>
<div class="container">
    <h2>Edit Payment</h2>
    <form action="${pageContext.request.contextPath}/PaymentEditServlet" method="post">
        <input type="hidden" name="paymentId" value="${payment.paymentID}">
        Order ID: <input type="number" name="orderId" value="${payment.orderID}" required><br>
        Payment Method:
        <select name="paymentMethod" required>
            <option value="Credit Card" ${payment.paymentMethod == 'Credit Card' ? 'selected' : ''}>Credit Card</option>
            <option value="PayPal" ${payment.paymentMethod == 'PayPal' ? 'selected' : ''}>PayPal</option>
        </select><br>
        Amount: <input type="number" step="0.01" name="amount" value="${payment.totalPrice}" required><br>
        Date: <input type="date" name="paymentDate" value="<fmt:formatDate value='${payment.paymentDate}' pattern='yyyy-MM-dd'/>" required><br>
        Status:
        <select name="status" required>
            <option value="Pending" ${payment.status == 'Pending' ? 'selected' : ''}>Pending</option>
            <option value="Completed" ${payment.status == 'Completed' ? 'selected' : ''}>Completed</option>
            <option value="Cancelled" ${payment.status == 'Cancelled' ? 'selected' : ''}>Cancelled</option>
        </select><br>
        <button type="submit">Update Payment</button>
    </form>
</div>
</body>
</html>