
<%@page import="com.mycompany.labs.model.User"%>
<%
    String email = request.getParameter("email");
    String firstName = request.getParameter("firstName");
    String lastName = request.getParameter("lastName");
    String password = request.getParameter("password");
    String mobile = request.getParameter("mobile");
    String gender = request.getParameter("gender");
    String favouriteColour = request.getParameter("favouriteColour");

    User user = new User();
    user.setEmail(email);
    user.setFirstName(firstName);
    user.setLastName(lastName);
    user.setPassword(password);
    user.setMobile(mobile);
    user.setGender(gender);
    user.setFavouriteColour(favouriteColour);

    session.setAttribute("currentUser", user);
    application.setAttribute(email, user);

%>
<html>
<head>
    <title>Welcome</title>
    <link rel="stylesheet" href="css/demo.css">
</head>
<body>
    <h2>Welcome, <%= firstName %>!</h2>
    <p>Your details have been saved. Proceed to the main page.</p>
    <a href="home">Go to Main Page</a>
</body>
</html>
