<%-- 
    Document   : newjsp
    Created on : 2025年3月12日, 下午5:48:50
    Author     : 20828
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        
        <p>Hello world!</p>
        
        <%
           for(int i=0; i<10; i++){
              if(i%2==0){
                %> 
                <h2 style="color: blue"> Kai Fang </h2>     
        <%
              }
              
              else{
               %>
               <h2 style="color: red"> Kai Fang</h2>
        <%
               
              }
           }
        %>
            
    </body>
</html>
