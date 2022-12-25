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
        <h3 class=""><b>My trainer collaboration</b></h3>
        <button onclick="getTrainerCollaborations();" class="w3-button w3-theme-dark w3-ripple w3-medium w3-margin-bottom w3-right">Update Status</button>
        <button onclick="searchNewTrainer();" class="w3-button w3-theme w3-ripple w3-right w3-medium w3-margin-bottom w3-margin-right">New Trainer</button>
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
        <table class="w3-table-all" id="workout_request" style="visibility : hidden">
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
        <script>

            function myload() {
                getTrainerCollaborations();
            }

            function createTrainerCollaboration()
            {
                let hidden_field = document.getElementById("add_trainer_table_hidden");
                ajaxcall(window.location.href + "?createTrainerCollaboration=" + hidden_field.value).then((jsonresponse) => {
                    printdebug("Risposta createTrainerCollaboration: ");
                    printdebug(jsonresponse);
                }, (httpstatus) => {
                    printdebug("Errore richiesta: " + httpstatus);
                });
            }

            function selectPossibleNewTrainer(trainer_id)
            {
                let hidden_field = document.getElementById("add_trainer_table_hidden");
                let old_selected_trainer = document.getElementById(hidden_field.value.replace("trainer_id_", ""));
                hidden_field.value = trainer_id.replace("trainer_id_", "");
                let selected_trainer = document.getElementById(trainer_id);

                if(old_selected_trainer !== null)
                    old_selected_trainer.classList.remove("w3-theme");
                selected_trainer.classList.add("w3-theme");
            }

            function searchNewTrainer(){
                ajaxcall(window.location.href + "?getNewPossibleTrainer=true").then((jsonresponse) => {
                    printdebug("Risposta getNewPossibleTrainer: ");
                    printdebug(jsonresponse);
                    let mytable = document.getElementById("add_trainer_table");
                    let old_tbody = mytable.getElementsByTagName('tbody')[0];
                    let tbody = document.createElement('tbody');

                    if(jsonresponse.length > 0)
                    {
                        for(let i = 0; i < jsonresponse.length; i++)
                        {
                            let tr = document.createElement("tr");
                            let name = jsonresponse[i].name + " " + jsonresponse[i].surname;

                            tr.id = "trainer_id_" + jsonresponse[i].professional_id;
                            tr.addEventListener("click", selectPossibleNewTrainer.bind(tr, tr.id));
                            tr.style.cursor = "pointer";

                            let td = document.createElement("td");
                            td.textContent = jsonresponse[i].name + " " + jsonresponse[i].surname;
                            tr.appendChild(td);

                            td = document.createElement("td");
                            td.textContent = jsonresponse[i].title;
                            tr.appendChild(td);

                            td = document.createElement("td");
                            td.textContent = jsonresponse[i].description;
                            tr.appendChild(td);

                            tbody.appendChild(tr);
                        }
                    }
                    else
                    {
                        let tr = document.createElement("tr");
                        let td = document.createElement("td");
                        td.classList.add("w3-center");
                        td.textContent = "No trainers to add!";
                        td.colSpan = 4;
                        tr.appendChild(td);
                        tbody.appendChild(tr);
                    }

                    //Replace the old body with the new one
                    mytable.replaceChild(tbody, old_tbody);
                }, (httpstatus) => {
                    printdebug("Errore richiesta: " + httpstatus);
                });

                document.getElementById('new_trainer_collab').style.display='block';
            }

            function getTrainerCollaborations() {
                ajaxcall(window.location.href + "?getTrainerCollaborations=true").then((jsonresponse) => {
                    printdebug("Risposta getTrainerCollaborations: ");
                    printdebug(jsonresponse);
                    let myothertable = document.getElementById("workout_request");
                    myothertable.style.visibility = "hidden";
                    //myothertable.previousSibling.style.visibility = "hidden";
                    //myothertable.previousSibling.previousSibling.style.visibility = "hidden";

                    let mytable = document.getElementById("trainer_collaboration");
                    let old_tbody = mytable.getElementsByTagName('tbody')[0];
                    let tbody = document.createElement('tbody');

                    if(jsonresponse.length > 0)
                    {
                        for(let i = 0; i < jsonresponse.length; i++)
                        {
                            let tr = document.createElement("tr");

                            tr.id = "collaboration_id_" + jsonresponse[i].collaboration_id;
                            tr.addEventListener("click", getWorkoutRequest.bind(tr, jsonresponse[i].collaboration_id));
                            tr.style.cursor = "pointer";

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

                    //Replace the old body with the new one
                    mytable.replaceChild(tbody, old_tbody);
                }, (httpstatus) => {
                    printdebug("Errore richiesta: " + httpstatus);
                });
            }

            function getWorkoutRequest(collaboration_id) {
                ajaxcall(window.location.href + "?getWorkoutRequest=" + collaboration_id).then((jsonresponse) => {
                    printdebug("Risposta getWorkoutRequest: ");
                    printdebug(jsonresponse);

                    let mytable = document.getElementById("workout_request");
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
                            tr.addEventListener("click", getWorkoutRequest.bind(tr, jsonresponse[i].collaboration_id));

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
                        td.textContent = "No workout request yet!";
                        td.colSpan = 3;
                        tr.appendChild(td);
                        tbody.appendChild(tr);
                    }

                    mytable.style.visibility = "visible";
                    //.previousSibling.style.visibility = "visible";
                    //mytable.previousSibling.previousSibling.style.visibility = "visible";

                }, (httpstatus) => {
                    printdebug("Errore richiesta: " + httpstatus);
                });
            }

            function filter(elem) {
                console.log(elem);
                let search_bar = document.getElementById(elem.id);
                let table = document.getElementById((elem.id + "").replace("search_bar", "table"));
                let filter = search_bar.value.toUpperCase();
                let tr = table.getElementsByTagName("tr");
                for (let i = 0; i < tr.length; i++) {
                    let td = tr[i].getElementsByTagName("td")[0];
                    if (td) {
                        let txtValue = td.textContent || td.innerText;
                        if (txtValue.toUpperCase().indexOf(filter) > -1) {
                            tr[i].style.display = "";
                        } else {
                            tr[i].style.display = "none";
                        }
                    }
                }
            }

        </script>
        <div id="new_trainer_collab" class="w3-modal">
            <div class="w3-modal-content w3-card-4 w3-animate-zoom" style="max-width:600px">
                <div class="w3-center">
                    <br/>
                    <h3 class="w3-text-theme"><b>Add new Trainer</b></h3>
                    <span onclick="document.getElementById('new_trainer_collab').style.display='none'" class="w3-button w3-xlarge w3-hover-red w3-theme w3-display-topright" title="Close Modal">&times;</span>
                </div>
                <br><br>
                <div class="w3-margin">
                    <input class="w3-input w3-border w3-padding" type="text" placeholder="Search for names.." id="add_trainer_search_bar" onkeyup="filter(this);">
                    <table class="w3-table-all w3-margin-top" id="add_trainer_table">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Title</th>
                            <th>Description</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                    <input type="hidden" id="add_trainer_table_hidden" name="add_trainer_table_hidden" value="">
                    <button class="w3-button w3-block w3-theme w3-section w3-padding" type="button" onclick="createTrainerCollaboration()">Request collaboration</button>
                </div>
                <br>
                </div>
        </div>
        <div id="new_workout" class="w3-modal">
            <div class="w3-modal-content w3-card-4 w3-animate-zoom" style="max-width:600px">
                <div class="w3-center"><br>
                    <span onclick="document.getElementById('new_workout').style.display='none'" class="w3-button w3-xlarge w3-hover-red w3-theme w3-display-topright" title="Close Modal">&times;</span>
                </div>
                <form class="w3-container" action="" method="get">
                    <br><br>
                    <label for="workout_goal"><b>Workout goal</b></label>
                    <textarea class="w3-input w3-border w3-margin-bottom" style="resize:none;" name="health_notes" id="workout_goal" required ></textarea>
                    <label for="workout_days"><b>Workout days</b></label>
                    <input class="w3-input w3-border w3-margin-bottom" type="number" name="workout_days" required id="workout_days" value="3" min="1" step="1" />
                    <label for="health_notes"><b>Health Notes</b></label>
                    <textarea class="w3-input w3-border w3-margin-bottom" style="resize:none;" name="health_notes" id="health_notes" required ></textarea>
                    <button class="w3-button w3-block w3-theme w3-section w3-padding" type="submit">Request Workout</button>
                </form>
            </div>
        </div>
    </div>

    <div id="nutritionist_subpage" class="w3-container subpage" style="display:none">
        <h3 class=""><b>My trainer collaboration</b></h3>
        <h5 class="">To do</h5>
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