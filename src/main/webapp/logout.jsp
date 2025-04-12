<%-- 
    Document   : logout
    Created on : 2025?4?2?, ??6:20:40
    Author     : 20828
--%>

<%
    session.invalidate();
    response.sendRedirect("login.jsp");
%>
