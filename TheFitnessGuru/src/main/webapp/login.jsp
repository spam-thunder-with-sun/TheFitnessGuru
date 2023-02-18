<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>

<jsp:include page="head.jsp" />
<body>

<header class="w3-container w3-theme">
    <h1>Login</h1>
</header>
<div class="w3-third">&nbsp;</div>
<div class="w3-container w3-third w3-margin-top">
    <form class="w3-container w3-card-4" action="" method="get" onsubmit="return loginRequest();">
        <br>
        <label for="username"><b>Username</b></label>
        <input class="w3-input w3-border w3-margin-bottom" type="text" id="username" name="username" required>
        <label for="password"><b>Password</b></label>
        <input class="w3-input w3-border w3-margin-bottom" type="password" id="password" name="password" required>
        <button type="submit" class="w3-button w3-section w3-theme w3-ripple"> Log in </button>
        <p class="w3-text-red w3-text"><b id="myErrorMessage">&nbsp;</b></p>
        <p>Are you not registered yet? <a class="w3-hover-text-theme" href="signin">Sign in!</a></p>
    </form>
</div>
<div class="w3-third">&nbsp;</div>
<script>
    function loginRequest()
    {
        let username = document.getElementById("username");
        let password = document.getElementById("password");
        let myErrorMessage = document.getElementById("myErrorMessage");

        let data = {
            "username": username.value,
            "password": password.value
        }

        ajaxcall(window.location.href, "POST", data).then(
            (jsonresponse) =>
            {
                printdebug("Risposta loginRequest: ");
                printdebug(jsonresponse);

                myErrorMessage.innerHTML = "&nbsp;";
            },
            function (httpstatus)
            {
                printdebug("Errore login: " + httpstatus);

                if(httpstatus == 401)
                {
                    myErrorMessage.textContent = "Username or Password incorrect";
                    //username.value = "";
                    password.value = "";
                }
            }
        );

        return false;
    }
</script>
</body>
</html>
