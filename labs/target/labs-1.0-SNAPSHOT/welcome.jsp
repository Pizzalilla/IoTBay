<%-- 
    Document   : welcome
    Created on : 2025年3月12日, 下午6:38:25
    Author     : 20828
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.LinkedList"%>
<%@page import="com.mycompany.labs.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    </head>
    <body>
        <nav class="navbar navbar-expand-lg bg-dark navbar-dark">
            <div class="container-fluid">
                <a class="navbar-brand" href="/">IoTBay</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="/">Home</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="/">Orders</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="/">Shipments</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="/">Payments</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="/">Users</a>
                        </li>
                    </ul>
                    <form class="d-flex" role="search">
                        <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                        <button class="btn btn-outline-success" type="submit">Search</button>
                    </form>
                </div>
            </div>
        </nav>
        <div class="alert alert-info mt-3">


            <%
                if (request.getSession().getAttribute("userList") == null) {
                    request.getSession().setAttribute("userList", new LinkedList<User>());
                }

                List<User> userList = (List<User>) request.getSession().getAttribute("userList");

                String type = request.getParameter("type");
                if (type.equals("register")) {
                    User user = new User();
                    user.setEmail(request.getParameter("email"));
                    user.setPassword(request.getParameter("password"));
                    user.setFirstName(request.getParameter("firstName"));
                    user.setLastName(request.getParameter("lastName"));
                    user.setMobile(request.getParameter("mobile"));
                    userList.add(user);
            %>
            <h3>You have successfully registerd. Please click <a href="login.jsp">here</a> to login.</h3>
            <%
                } else {
                    String email = request.getParameter("email");
                    String password = request.getParameter("password");
                    boolean isValid = false;
                    for (User user : userList) {
                        if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                            isValid = true;
                            request.getSession().setAttribute("currentUser", user);
                        }
                    }
                    if (!isValid) {
                        response.sendRedirect("login.jsp");
                    } else {
                        response.sendRedirect("main.jsp");
                    }
                }
            %>
        </div>
    </body>
</html>
