<%-- 
    Document   : index
    Created on : 2025年3月16日, 下午2:26:50
    Author     : 20828
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
        <link href="css/demo.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
       <h1>Welcome to IoTBay</h1>
        
        <%         
            String username = (String) session.getAttribute("username");
        %>

        <% if (username != null) { %>
            <h2>Hello, <%= username %>!</h2>
            <a href="logout.jsp">Logout</a>
        <% } else { %>
        <div class="button-container">
            <a href="register.jsp">Register</a> 
            <a href="login.jsp">Login</a>
        <% } %>
    </body>
</html>
