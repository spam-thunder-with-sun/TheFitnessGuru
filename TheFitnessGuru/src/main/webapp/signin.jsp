<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="myErrorBean" class="it.unitn.disi.sdeproject.beans.ErrorMessage" scope="request"/>
<html>

<jsp:include page="head.jsp" />
<body>
<header class="w3-container w3-indigo">
    <h1>Sign In</h1>
</header>
<div class="w3-third">&nbsp;</div>
<div class="w3-container w3-third w3-margin-top">
    <form class="w3-container w3-card-4" action="" method="post" id="myform" autocomplete="on">
        <p><input class="w3-input" type="text" style="width:90%" name="name" required id="name">
            <label for="name">Name</label></p>
        <p><input class="w3-input" type="text" style="width:90%" name="surname" required id="surname">
            <label for="surname">Surname</label></p>
        <p><input class="w3-input" type="date" style="width:90%" name="birthday" required id="birthday">
            <label for="birthday">Birthday</label></p>
        <p><input class="w3-input" type="text" style="width:90%" name="username" required id="username">
            <label for="username">Username</label></p>
        <p><input id="new-password-text-field" type="password" autocomplete="new-password" name="password" required class="w3-input" style="width:90%" />
            <label for="new-password-text-field">Password</label></p>
        <p><input id="confirm-password-text-field" type="password" autocomplete="new-password" name="confirm_password" required class="w3-input" style="width:90%" />
            <label for="confirm-password-text-field">Repeat Password</label></p>
        <p><button type="submit" class="w3-button w3-section w3-indigo w3-ripple"> Sign in! </button></p>
        <!--<p class="w3-text-red">&nbsp;</p>-->
        <p class="w3-text-red w3-text"><b><jsp:getProperty name="myErrorBean" property="errorMessage" /></b></p>
        <p>Are you alredy registered? <a class="w3-hover-text-indigo" href="login">Log in!</a></p>
    </form>
</div>
<div class="w3-third">&nbsp;</div>

</body>
</html>
