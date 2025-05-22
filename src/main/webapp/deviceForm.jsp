<%--
  Created by IntelliJ IDEA.
  User: 20828
  Date: 2025/5/14
  Time: 21:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.mycompany.labs.model.Device" %>
<%
  Device device = (Device) request.getAttribute("device");
  boolean isUpdate = (device != null);
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title><%= isUpdate ? "Update Device" : "Create Device" %></title>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">

<div class="container mt-5">
  <h2 class="mb-4"><%= isUpdate ? "Update Device" : "Create New Device" %></h2>

  <form action="updateDevice" method="post" enctype="multipart/form-data" class="border p-4 bg-white rounded shadow-sm">
    <% if (isUpdate) { %>
    <input type="hidden" name="deviceId" value="<%= device.getDeviceId() %>">
    <% } %>

    <div class="mb-3">
      <label class="form-label">Device Name</label>
      <input type="text" name="deviceName" class="form-control" required value="<%= isUpdate ? device.getDeviceName() : "" %>">
    </div>

    <div class="mb-3">
      <label class="form-label">Price</label>
      <input type="number" step="0.01" name="price" class="form-control" required value="<%= isUpdate ? device.getPrice() : "" %>">
    </div>

    <div class="mb-3">
      <label class="form-label">Type</label>
      <input type="text" name="type" class="form-control" required value="<%= isUpdate ? device.getType() : "" %>">
    </div>

    <div class="mb-3">
      <label class="form-label">Stock Quantity</label>
      <input type="number" name="stockQty" class="form-control" required value="<%= isUpdate ? device.getStockQty() : "" %>">
    </div>

    <div class="mb-3">
      <label class="form-label">Upload Image</label>
      <input type="file" name="image" class="form-control" <%= isUpdate ? "" : "required" %>>
      <% if (isUpdate && device.getImageName() != null) { %>
      <p class="mt-2">Current image: <strong><%= device.getImageName() %></strong></p>
      <% } %>
    </div>

    <button type="submit" class="btn btn-primary"><%= isUpdate ? "Update" : "Create" %></button>
  </form>
</div>

</body>
</html>

