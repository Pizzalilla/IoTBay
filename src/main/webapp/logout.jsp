<%@ page import="com.mycompany.labs.dao.LogDao" %>
<%@ page import="java.sql.SQLException" %>

<%
    String logId = (String) session.getAttribute("logId");

    if (logId != null) {
        try {
            LogDao logDao = new LogDao();
            logDao.updateLogoutTime(logId);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    session.invalidate();
    response.sendRedirect("login.jsp");
%>
