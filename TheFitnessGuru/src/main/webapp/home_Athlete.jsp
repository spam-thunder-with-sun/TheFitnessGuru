<%@ page contentType="text/html;charset=UTF-8" %>
<jsp:useBean id="user" class="it.unitn.disi.sdeproject.beans.Athlete" scope="session"/>
<html>

<jsp:include page="head.jsp" />
<body onload="myload()">

<header class="w3-container w3-theme">
    <h1>Home Athlete</h1>
</header>
<div class="w3-quarter">
    <!-- Sidebar -->
    <div class="w3-sidebar w3-bar-block w3-border-right" style="display:none" id="mySidebar">
        <button onclick="w3_close()" class="w3-bar-item w3-button w3-large w3-text-red w3-hover-text-theme w3-ripple">Close</button>
        <button class="w3-bar-item w3-button w3-hover-theme w3-ripple" onclick="load_subpage('trainer_subpage')">Trainer</button>
        <button class="w3-bar-item w3-button w3-hover-theme w3-ripple" onclick="load_subpage('nutritionist_subpage')">Nutritionist</button>
        <button class="w3-bar-item w3-button w3-hover-theme w3-ripple" onclick="load_subpage('userinfo_subpage')">User Info</button>
    </div>
    <button class="w3-button w3-text-theme w3-xlarge w3-ripple" onclick="w3_open()">☰</button>
</div>
<div class="w3-container w3-half w3-margin-top">

    <div id="trainer_subpage" class="w3-container subpage">
        <br/>
        <h3 class=""><b>My trainer collaboration</b> <button onclick="document.getElementById('new_trainer_collab').style.display='block'" class="w3-button w3-theme w3-ripple w3-right w3-medium">New Trainer</button></h3>
        <table class="w3-table-all w3-hoverable" id="trainer_collaboration">
            <thead>
            <tr class="w3-theme">
                <th colspan="2">Trainer Full Name</th>
                <th>Status</th>
                <!--<th>Details</th>-->
            </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
        <br/><br/>
        <script>

            function myload() {
                getTrainerCollaborations();
            }

            function getTrainerCollaborations() {
                ajaxcall(window.location.href + "?getTrainerCollaborations=true").then((jsonresponse) => {
                    printdebug("Risposta: ");
                    printdebug(jsonresponse);
                    let mytable = document.getElementById("trainer_collaboration");
                    let old_tbody = mytable.getElementsByTagName('tbody')[0];
                    let tbody = document.createElement('tbody');

                    if(jsonresponse.length > 0)
                    {
                        for(let i = 0; i < jsonresponse.length; i++)
                        {
                            let tr = document.createElement("tr");
                            let name = jsonresponse[i].name + " " + jsonresponse[i].surname;
                            let status = jsonresponse[i].status;

                            tr.id = "collaboration_id_" + jsonresponse[i].collaboration_id;

                            let td = document.createElement("td");
                            td.textContent = jsonresponse[i].name;
                            tr.appendChild(td);

                            td = document.createElement("td");
                            td.textContent = jsonresponse[i].surname;
                            tr.appendChild(td);

                            td = document.createElement("td");
                            td.textContent = jsonresponse[i].status;
                            tr.appendChild(td);

                            tbody.appendChild(tr);
                        }
                    }
                    else
                    {
                        let tr = document.createElement("tr");
                        let td = document.createElement("td");
                        td.classList.add("w3-center");
                        td.textContent = "No collaboration yet!";
                        td.colSpan = 3;
                        tr.appendChild(td);
                        tbody.appendChild(tr);
                    }
                    /*
            <tr><td colspan="3" class="w3-center">No collaboration yet!</td></tr>
                     */

                    //Replace the old body with the new one
                    mytable.replaceChild(tbody, old_tbody);
                }, (httpstatus) => {
                    printdebug("Errore richiesta: " + httpstatus);
                });
            }
        </script>

        <div id="new_trainer_collab" class="w3-modal">
            <div class="w3-modal-content w3-card-4 w3-animate-zoom" style="max-width:600px">
                <div class="w3-center"><br>
                    <span onclick="document.getElementById('new_trainer_collab').style.display='none'" class="w3-button w3-xlarge w3-hover-red w3-theme w3-display-topright" title="Close Modal">&times;</span>
                    <img src="img_avatar4.png" alt="Avatar" style="width:30%" class="w3-circle w3-margin-top">
                </div>
                <form class="w3-container" action="/action_page.php">
                    <div class="w3-section">
                        <label><b>Username</b></label>
                        <input class="w3-input w3-border w3-margin-bottom" type="text" placeholder="Enter Username" name="usrname" required>
                        <label><b>Password</b></label>
                        <input class="w3-input w3-border" type="password" placeholder="Enter Password" name="psw" required>
                        <button class="w3-button w3-block w3-theme w3-section w3-padding" type="submit">Request collaboration</button>
                    </div>
                </form>
                <div class="w3-container w3-border-top w3-padding-16 w3-light-grey">
                    <button onclick="document.getElementById('new_trainer_collab').style.display='none'" type="button" class="w3-button w3-red">Cancel</button>
                    <span class="w3-right w3-padding w3-hide-small">Forgot <a href="#">password?</a></span>
                </div>
            </div>
        </div>
    </div>

    <div id="nutritionist_subpage" class="w3-container subpage" style="display:none">
        <h2>Tokyo</h2>
        <p>Tokyo is the capital of Japan.</p>
    </div>

    <div id="userinfo_subpage" class="w3-container subpage" style="display:none">
        <h3 class=""><b>My Info</b></h3>
        <h4><b class="w3-text-theme">Name: </b><jsp:getProperty name="user" property="name" /></h4>
        <h4><b class="w3-text-theme">Surname: </b><jsp:getProperty name="user" property="surname" /></h4>
        <h4><b class="w3-text-theme">Username: </b><jsp:getProperty name="user" property="username" /></h4>
        <h4><b class="w3-text-theme">Gender: </b><jsp:getProperty name="user" property="gender" /></h4>
        <h4><b class="w3-text-theme">Birthdate: </b><jsp:getProperty name="user" property="birthdate" /></h4>
        <h4><b class="w3-text-theme">Height: </b><jsp:getProperty name="user" property="height" /></h4>
        <h4><b class="w3-text-theme">Weight: </b><jsp:getProperty name="user" property="weight" /></h4>
        <h4><b class="w3-text-theme">Sport: </b><jsp:getProperty name="user" property="sport" /></h4>
    </div>
</div>

<div class="w3-quarter">
    <div class="w3-bar w3-margin-top">
        <form action="home" method="get" class="w3-margin-right">
            <button type="submit" name="logout" value="ok" class="w3-button w3-right w3-red w3-ripple">Logout</button>
        </form>
    </div></div>
</body>
</html>