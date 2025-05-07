<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create Order</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #1e1e1e;
            color: #ccc;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 400px;
            margin: 60px auto;
            padding: 20px;
            background-color: #2d2d2d;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.7);
        }
        h2 {
            text-align: center;
            color: #fff;
        }
        label {
            display: block;
            margin-top: 12px;
        }
        input[type="text"], input[type="date"], select {
            width: 100%;
            padding: 8px;
            margin-top: 4px;
            border: 1px solid #555;
            background-color: #3c3c3c;
            color: #fff;
            border-radius: 4px;
        }
        input[type="submit"] {
            margin-top: 20px;
            width: 100%;
            padding: 10px;
            background-color: #4a90e2;
            border: none;
            color: white;
            font-weight: bold;
            border-radius: 4px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #357abd;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Create New Order</h2>
        <form action="CreateOrderServlet" method="post">
            <label for="customerID">Customer ID</label>
            <input type="text" name="customerID" required>

            <label for="staffID">Staff ID</label>
            <input type="text" name="staffID" required>

            <label for="orderDate">Order Date</label>
            <input type="date" name="orderDate" required>

            <label for="status">Status</label>
            <select name="status" required>
                <option value="Pending">Pending</option>
                <option value="Confirmed">Confirmed</option>
                <option value="Cancelled">Cancelled</option>
            </select>

            <input type="submit" value="Create Order">
        </form>
    </div>
</body>
</html>
