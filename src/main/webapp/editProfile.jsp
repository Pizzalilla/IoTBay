<%@ page import="com.mycompany.labs.model.User" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%
  User user = (User) session.getAttribute("currentUser");
  if (user == null) {
    response.sendRedirect("login.jsp");
    return;
  }
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Edit Profile</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-5">
  <h2>Edit Profile</h2>

  <% if (request.getAttribute("error") != null) { %>
  <div class="alert alert-danger">
    <%= request.getAttribute("error") %>
  </div>
  <% } %>

  <form action="UpdateProfileServlet" method="post" class="mt-4">
    <div class="mb-3">
      <label for="firstName" class="form-label">First Name</label>
      <input type="text" name="firstName" id="firstName" value="<%= user.getFirstName() %>" class="form-control" required>
    </div>

    <div class="mb-3">
      <label for="lastName" class="form-label">Last Name</label>
      <input type="text" name="lastName" id="lastName" value="<%= user.getLastName() %>" class="form-control" required>
    </div>

    <div class="mb-3">
      <label for="email" class="form-label">Email</label>
      <input type="email" name="email" id="email" value="<%= user.getEmail() %>" class="form-control" required>
    </div>

    <div class="mb-3">
      <label for="mobile" class="form-label">Mobile</label>
      <input type="text" name="mobile" id="mobile" value="<%= user.getMobile() != null ? user.getMobile() : "" %>" class="form-control">
    </div>

    <div class="mb-3">
      <label for="gender" class="form-label">Gender</label>
      <select name="gender" id="gender" class="form-select">
        <option value="">-- Select --</option>
        <option value="Male" <%= "Male".equals(user.getGender()) ? "selected" : "" %>>Male</option>
        <option value="Female" <%= "Female".equals(user.getGender()) ? "selected" : "" %>>Female</option>
        <option value="Other" <%= "Other".equals(user.getGender()) ? "selected" : "" %>>Other</option>
      </select>
    </div>

    <div class="mb-3">
      <label for="address" class="form-label">Address</label>
      <textarea name="address" id="address" class="form-control" rows="3"><%= user.getAddress() %></textarea>
    </div>

    <% if ("staff".equals(user.getType())) { %>
    <div class="mb-3">
      <label for="position" class="form-label">Position</label>
      <input type="text" name="position" id="position" value="<%= user.getPosition() != null ? user.getPosition() : "" %>" class="form-control" required>
    </div>
    <% } %>

    <div class="mb-3">
      <label for="password" class="form-label">New Password (optional)</label>
      <input type="password" name="password" id="password" class="form-control" placeholder="Leave blank to keep current password">
    </div>

    <button type="submit" class="btn btn-primary">Save Changes</button>
    <a href="main.jsp" class="btn btn-secondary ms-2">Cancel</a>
  </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
