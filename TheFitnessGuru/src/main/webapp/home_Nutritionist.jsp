<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="user" class="it.unitn.disi.sdeproject.beans.Nutritionist" scope="session"/>
<!DOCTYPE html>
<html>

<jsp:include page="head.jsp" />
<body onload="myload()">

<header class="w3-container w3-theme">
    <h1>Home Nutritionist</h1>
</header>
<div class="w3-quarter">
    <!-- Sidebar -->
    <div class="w3-sidebar w3-bar-block w3-border-right" style="display:none" id="mySidebar">
        <button onclick="w3_close()" class="w3-bar-item w3-button w3-large w3-text-red w3-hover-text-theme w3-ripple">Close</button>
        <button class="w3-bar-item w3-button w3-hover-theme w3-ripple" onclick="load_subpage('collaboration_subpage')">Collaboration</button>
        <button class="w3-bar-item w3-button w3-hover-theme w3-ripple" onclick="load_subpage('diet_subpage')">Diet Request</button>
        <button class="w3-bar-item w3-button w3-hover-theme w3-ripple" onclick="load_subpage('userinfo_subpage')">User Info</button>
    </div>
    <button class="w3-button w3-text-theme w3-xlarge w3-ripple" onclick="w3_open()">☰</button>
</div>
<div class="w3-container w3-half w3-margin-top">
    <div id="collaboration_subpage" class="w3-container subpage">
        <jsp:include page="home_Nutritionist/collaboration_subpage.jsp" />
    </div>

    <div id="diet_subpage" class="w3-container subpage" style="display:none">
        <jsp:include page="home_Nutritionist/diet_subpage.jsp" />
    </div>

    <div id="userinfo_subpage" class="w3-container subpage" style="display:none">
        <h3 class=""><b>My Info</b></h3>
        <h4><b class="w3-text-theme">Name: </b><jsp:getProperty name="user" property="name" /></h4>
        <h4><b class="w3-text-theme">Surname: </b><jsp:getProperty name="user" property="surname" /></h4>
        <h4><b class="w3-text-theme">Email: </b><jsp:getProperty name="user" property="email" /></h4>
        <h4><b class="w3-text-theme">Username: </b><jsp:getProperty name="user" property="username" /></h4>
        <h4><b class="w3-text-theme">Gender: </b><jsp:getProperty name="user" property="gender" /></h4>
        <h4><b class="w3-text-theme">Birthdate: </b><jsp:getProperty name="user" property="birthdate" /></h4>
        <h4><b class="w3-text-theme">Title: </b><jsp:getProperty name="user" property="title" /></h4>
        <h4><b class="w3-text-theme">Description: </b><jsp:getProperty name="user" property="description" /></h4>
    </div>
</div>

<div class="w3-quarter">
    <div class="w3-bar w3-margin-top">
        <form action="home" method="get" class="w3-margin-right">
            <button type="submit" name="logout" value="ok" class="w3-button w3-right w3-red w3-ripple">Logout</button>
        </form>
    </div>
</div>

<script>
    function myload()
    {
        //Load Athlete collab
        getAthleteCollaborations();
        //Load Diet request
        getDietRequest();
    }
</script>
</body>
</html>