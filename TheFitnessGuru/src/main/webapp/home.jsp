<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<jsp:include page="head.jsp" />
<body>

<header class="w3-container w3-indigo">
  <h1>Home</h1>
</header>
<div class="w3-third">&nbsp;</div>
<div class="w3-container w3-third w3-margin-top">
  <h3>Home Page</h3>
</div>

<div class="w3-third">
  <div class="w3-bar w3-margin-top">
  <form action="home" method="get" class="w3-margin-right">
    <button type="submit" name="logout" value="ok" class="w3-button w3-right w3-red w3-ripple">Logout</button>
  </form>
</div></div>

</body>
</html>