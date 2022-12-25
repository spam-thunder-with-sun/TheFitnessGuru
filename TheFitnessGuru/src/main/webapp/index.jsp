<%@ page import="it.unitn.disi.sdeproject.thefitnessguru.Login" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body onload="foo();">
<h1><%= "Hello World!" %>
</h1>
<br/>
<a href="login">Login Servlet</a><br/>
<a href="login.jsp">login.jsp</a><br/>
<a href="signin">Signin Servlet</a><br/>
<a href="signin.jsp">signin.jsp</a><br/>
<script>
    function foo() {
        let foo = window.location;
        window.location.replace(foo + "login");
    }
</script>
</body>
</html>