<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="myErrorBean" class="it.unitn.disi.sdeproject.beans.ErrorMessage" scope="request"/>
<!DOCTYPE html>
<html>

<jsp:include page="head.jsp" />
<body>
<header class="w3-container w3-theme">
    <h1>Sign In</h1>
</header>
<div class="w3-quarter">&nbsp;</div>
<div class="w3-container w3-half w3-margin-top w3-margin-bottom">
    <form class="w3-container w3-card-4" action="" method="get" id="myform" autocomplete="on" onsubmit="return signinRequest(event)">
        <h4 class="w3-text-theme"><b>Anagraphic Data</b></h4>
        <label for="name"><b>Name</b></label>
        <input class="w3-input w3-border w3-margin-bottom" type="text" name="name" required id="name" />
        <label for="surname"><b>Surname</b></label>
        <input class="w3-input w3-border w3-margin-bottom" type="text"  name="surname" required id="surname" />
        <label for="birthday"><b>Birthday</b></label>
        <input class="w3-input w3-border w3-margin-bottom" type="date" name="birthday" required id="birthday" />
        <label><b>Gender</b></label><br>
        <input class="w3-radio w3-margin-bottom" type="radio" name="gender" value="male" id="gender_male" checked />
        <label for="gender_male" class="w3-margin-right">Male</label>
        <input class="w3-radio w3-margin-bottom" type="radio" name="gender" value="female" id="gender_female" />
        <label for="gender_female">Female</label>
        <h4 class="w3-text-theme"><b>Account Data</b></h4>
        <label for="email"><b>Email</b></label>
        <input class="w3-input w3-border w3-margin-bottom" type="email" name="email" required id="email" />
        <label for="username"><b>Username</b></label>
        <input class="w3-input w3-border w3-margin-bottom" type="text" name="username" required id="username" />
        <label for="password"><b>Password</b></label>
        <input id="password" type="password" autocomplete="new-password" minlength=8 name="password" required class="w3-input w3-border w3-margin-bottom" />
        <label for="confirm_password"><b>Repeat Password</b></label>
        <input id="confirm_password" type="password" autocomplete="new-password" minlength=8 name="confirm_password" required class="w3-input w3-border w3-margin-bottom" />
        <h4 class="w3-text-theme"><b>User Data</b></h4>
        <label for="account_type"><b>Account Type</b></label>
        <select class="w3-select w3-margin-bottom w3-border w3-input w3-padding" name="account_type" id="account_type" onchange="accountTypeChange()" required>
            <option value="" disabled selected>Choose your option</option>
            <option value="athlete">Athlete</option>
            <option value="trainer">Trainer</option>
            <option value="nutritionist">Nutritionist</option>
        </select>
        <label for="sport" id="sport_label" style="display: none;"><b>Sport</b></label>
        <input class="w3-input w3-border w3-margin-bottom" type="text" name="sport" required id="sport" style="display: none;" />
        <label for="height" id="height_label" style="display: none;"><b>Height</b></label>
        <input class="w3-input w3-border w3-margin-bottom" type="number" name="height" required id="height" value="1.75" min="0.1" step="0.01" style="display: none;" />
        <label for="weight" id="weight_label" style="display: none;"><b>Weight</b></label>
        <input class="w3-input w3-border w3-margin-bottom" type="number" name="weight" required id="weight" value="70" min="30" step="0.1" style="display: none;" />
        <label for="title" id="title_label" style="display: none;"><b>Title</b></label>
        <input class="w3-input w3-border w3-margin-bottom" type="text" name="title" required id="title" style="display: none;" />
        <label for="description" id="description_label" style="display: none;"><b>Description</b></label>
        <textarea class="w3-input w3-border w3-margin-bottom" style="resize:none; display: none;" name="description" id="description" required ></textarea>
        <!------------------------------------------------------------------------------------------->
        <br>
        <p class="w3-text-red w3-text" style="margin: 0"><b id="myErrorMessage"><jsp:getProperty name="myErrorBean" property="errorMessage" /></b></p>
        <button type="submit" class="w3-button w3-section w3-theme w3-ripple"> Sign in! </button>
        <!--<p class="w3-text-red">&nbsp;</p>-->
        <p>Are you already registered? <a class="w3-hover-text-theme" href="login">Log in!</a></p>
    </form>
</div>
<div class="w3-quarter">&nbsp;</div>
<script>
    function signinRequest(event)
    {
        //Password check
        if(verifyPassword())
        {
            let myErrorMessage = document.getElementById("myErrorMessage");
            let myForm = document.getElementById("myform");

            let formData = new FormData(event.target);
            let data = Object.fromEntries(formData.entries());

            ajaxcall(window.location.href, "POST", data).then(
                (jsonresponse) =>
                {
                    printdebug("Risposta loginRequest: ");
                    printdebug(jsonresponse);

                    myForm.reset();
                    myErrorMessage.innerHTML = "&nbsp;";

                    //Goto home
                    window.location.href = window.location.href.substring(0, window.location.href.lastIndexOf("signin")) + "home";
                },
                function (httpstatus)
                {
                    printdebug("Errore login: " + httpstatus);

                    if(httpstatus == 400)
                    {
                        myErrorMessage.textContent = "Something went wrong";
                    }
                }
            );
        }

        return false;
    }

    function verifyPassword()
    {
        let password = document.getElementById('password').value;
        let confirm_password = document.getElementById('confirm_password').value;
        let myErrorMessage = document.getElementById('myErrorMessage');

        if(password === confirm_password)
        {
            myErrorMessage.innerText = "";
            return true;
        }

        myErrorMessage.innerText = "The passwords do not match";
        return false;
    }

    function accountTypeChange()
    {
        let account_type = document.getElementById('account_type').value;
        let sport = document.getElementById('sport');
        let sport_label = document.getElementById('sport_label');
        let height = document.getElementById('height');
        let height_label  = document.getElementById('height_label');
        let weight = document.getElementById('weight');
        let weight_label  = document.getElementById('weight_label');
        let title = document.getElementById('title');
        let title_label  = document.getElementById('title_label');
        let description = document.getElementById('description');
        let description_label  = document.getElementById('description_label');

        if(account_type.trim().toLowerCase() === "athlete")
        {
            sport.style.display = "";
            sport_label.style.display = "";
            height.style.display = "";
            height_label.style.display = "";
            weight.style.display = "";
            weight_label.style.display = "";
            title.style.display = "none";
            title_label.style.display = "none";
            description.style.display = "none";
            description_label.style.display = "none";

            sport.required = true;
            height.required = true;
            weight.required = true;
            title.required = false;
            description.required = false;
        }
        else
        {
            sport.style.display = "none";
            sport_label.style.display = "none";
            height.style.display = "none";
            height_label.style.display = "none";
            weight.style.display = "none";
            weight_label.style.display = "none";
            title.style.display = "";
            title_label.style.display = "";
            description.style.display = "";
            description_label.style.display = "";

            sport.required = false;
            height.required = false;
            weight.required = false;
            title.required = true;
            description.required = true;
        }
    }
</script>
</body>
</html>
