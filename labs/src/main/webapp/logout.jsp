<%-- 
    Document   : logout
    Created on : 2025?4?2?, ??6:20:40
    Author     : 20828
--%>

<%
    request.getSession().setAttribute("currentUser", null);
    response.sendRedirect("login.jsp");
%>
