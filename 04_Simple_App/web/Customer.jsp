<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: sandu
  Date: 2022-11-16
  Time: 14.57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>


<%-- Scriptless Syntax - Use to write java code --%>
<%
    String name = "Dasun";
    System.out.println("Hello There");
%>


<%-- Expression Syntax - Use to catch java variables --%>
<h1><%=name%></h1>


<%-- Declaration --%>
<%!String address = "Colombo";%>


</body>
</html>
