<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="myErrorBean" class="it.unitn.disi.sdeproject.beans.ErrorMessage" scope="request"/>
<html>

<jsp:include page="head.jsp" />
<body>

<header class="w3-container w3-indigo">
    <h1>Login</h1>
</header>
<div class="w3-third">&nbsp;</div>
<div class="w3-container w3-third w3-margin-top">
    <form class="w3-container w3-card-4" action="login" method="post">
        <br>
        <label for="username"><b>Username</b></label>
        <input class="w3-input w3-border w3-margin-bottom" type="text" id="username" name="username" required>
        <label for="password"><b>Password</b></label>
        <input class="w3-input w3-border w3-margin-bottom" type="password" id="password" name="password" required>
        <button type="submit" class="w3-button w3-section w3-indigo w3-ripple"> Log in </button>
        <!--<p class="w3-text-red">&nbsp;</p>-->
        <p class="w3-text-red w3-text"><b><jsp:getProperty name="myErrorBean" property="errorMessage" /></b></p>
        <p>Are you not registered yet? <a class="w3-hover-text-indigo" href="signin">Sign in!</a></p>
    </form>
</div>
<div class="w3-third">&nbsp;</div>

</body>
</html>
