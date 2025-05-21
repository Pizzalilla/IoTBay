<%-- 
    Document   : main.jsp
    Created on : 2025年3月12日, 下午6:15:36
    Author     : 20828
--%>
<%@page import="com.mycompany.labs.model.User"%>
<%@ page import="com.mycompany.labs.model.Device" %>
<%@ page import="java.util.List" %>
<%
    User currentUser = (User) request.getSession().getAttribute("currentUser");
    if (currentUser == null) {
        response.sendRedirect("login.jsp");
    } else {
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Main Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    </head>
    <body>
        <nav class="navbar navbar-expand-lg bg-dark navbar-dark">
            <div class="container-fluid">
                <a class="navbar-brand" href="./home">IoTBay</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="./home">Home</a>
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
                        <% if(currentUser.isStaff()) {
                            %>
                            <li class="nav-item">
                                <a class="nav-link active" aria-current="page" href="devices">Devices</a>
                            </li>

                            <li class="nav-item">
                                <a class="nav-link active" aria-current="page" href="/">Users</a>
                            </li>
                        <%
                        }%>
                    </ul>
                    <form class="d-flex" role="search">
                        <input class="form-control me-2" type="search" placeholder="Search name or type" name="keyword">
                        <button class="btn btn-outline-success" type="submit">Search</button>
                    </form>

                    <div class="ms-5 text-white">Welcome <%= currentUser.getFirstName()%> <%= currentUser.getLastName()%></div>
                    <div class="ms-5 text-white"><a href="logout.jsp">Logout</a></div>            
                </div>
            </div>
        </nav>
        <div class="container-md mt-4">
            <div class="row row-cols-lg-4 row-cols-md-3 row-cols-2 g-3">
                <%
                    List<Device> deviceList = (List<Device>) request.getAttribute("devices");
                    for(Device device : deviceList){
                        %>
                <div class="col">
                    <div class="card">
                        <img src="images/<%=device.getImageName()%>" class="card-img-top" height="200">
                        <div class="card-body">
                            <h5 class="card-title"><%=device.getDeviceName()%></h5>
                            <p class="card-text"><%=device.getType()%></p>
                            <p class="card-text text-danger fs-3">$<%=device.getPrice()%></p>
                            <a href="#" class="btn btn-primary">Add to Cart</a>
                        </div>
                    </div>
                </div>
                <%
                    }
                %>

            </div>
        </div>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    

</body>
</html>
<%
    }
%>