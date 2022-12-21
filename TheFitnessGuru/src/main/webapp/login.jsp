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
        <p>
            <input class="w3-input" type="text" name="username" style="width:90%" required>
            <label>Username</label></p>
        <p>
            <input class="w3-input" type="password" name="password"  style="width:90%" required>
            <label>Password</label></p>
        <p>
            <button type="submit" class="w3-button w3-section w3-indigo w3-ripple"> Log in </button>
        </p>
        <!--<p class="w3-text-red">&nbsp;</p>-->
        <p class="w3-text-red w3-text"><b><jsp:getProperty name="myErrorBean" property="errorMessage" /></b></p>
        <p>Are you not registered yet? <a class="w3-hover-text-indigo" href="signin">Sign in!</a></p>
    </form>
</div>
<div class="w3-third">&nbsp;</div>

</body>
</html>
