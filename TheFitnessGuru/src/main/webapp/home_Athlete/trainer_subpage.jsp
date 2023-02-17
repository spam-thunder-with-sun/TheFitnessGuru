<h3 class=""><b>My trainer collaboration</b></h3>
<button onclick="getTrainerCollaborations();" class="w3-button w3-theme-dark w3-ripple w3-medium w3-margin-bottom w3-right">Update Status</button>
<button onclick="searchNewTrainer();" class="w3-button w3-theme w3-ripple w3-right w3-medium w3-margin-bottom w3-margin-right">New Trainer</button>
<table class="w3-table-all w3-hoverable" id="trainer_collaboration">
    <thead>
    <tr class="w3-theme">
        <th>Trainer Name</th>
        <th>Request Status</th>
    </tr>
    </thead>
    <tbody>
    </tbody>
</table>
<br/><br/>
<button onclick="getWorkoutRequest();" id="workout_request_button_update_status" class="w3-button w3-theme-dark w3-ripple w3-medium w3-margin-bottom w3-right" style="visibility : hidden">Update Status</button>
<button onclick="newWorkoutRequestForm();" id="workout_request_button_new_workout" class="w3-button w3-theme w3-ripple w3-right w3-medium w3-margin-bottom w3-margin-right" style="visibility : hidden">New Workout</button>
<input type="hidden" id="workout_request_hidden_field" name="new_workout_hidden_field" value="">
<table class="w3-table-all w3-margin-bottom" id="workout_request" style="visibility : hidden">
    <thead>
    <tr class="w3-theme">
        <th>Request Date</th>
        <th>Workout Goals</th>
        <th>Workout Days</th>
        <th>Status</th>
    </tr>
    </thead>
    <tbody>
    </tbody>
</table>
<!------------------------- MODAL ------------------------------------->
<div id="new_trainer_collab" class="w3-modal">
    <div class="w3-modal-content w3-card-4 w3-animate-zoom w3-margin-bottom" style="max-width:600px">
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
            <button id="add_trainer_table_submit" class="w3-button w3-block w3-theme w3-section w3-padding" disabled type="button" onclick="createTrainerCollaboration()">Request collaboration</button>
        </div>
        <br>
        </div>
</div>
<div id="new_workout" class="w3-modal">
    <div class="w3-modal-content w3-card-4 w3-animate-zoom w3-margin-bottom" style="max-width:600px">
        <div class="w3-center">
            </br>
            <h3 class="w3-text-theme"><b>New Workout Request</b></h3>
            <span onclick="document.getElementById('new_workout').style.display='none'" class="w3-button w3-xlarge w3-hover-red w3-theme w3-display-topright" title="Close Modal">&times;</span>
        </div>
        <form class="w3-container" action="" method="get" onsubmit="return newWorkoutRequest();">
            <br><br>
            <input type="hidden" id="new_workout_hidden_field" name="new_workout_hidden_field" value="">
            <label for="new_workout_workout_goal"><b>Workout goal</b></label>
            <textarea class="w3-input w3-border w3-margin-bottom" style="resize:none;" name="workout_goal" id="new_workout_workout_goal" required ></textarea>
            <label for="new_workout_workout_days"><b>Workout days</b></label>
            <input class="w3-input w3-border w3-margin-bottom" type="number" name="workout_days" required id="new_workout_workout_days" value="3" min="1" step="1" max="7" />
            <label for="new_workout_health_notes"><b>Health Notes</b></label>
            <textarea class="w3-input w3-border w3-margin-bottom" style="resize:none;" name="health_notes" id="new_workout_health_notes" required ></textarea>
            <button class="w3-button w3-block w3-theme w3-section w3-padding" type="submit" >Request Workout</button>
        </form>
    </div>
</div>
<!------------------------- SCRIPTS ------------------------------------->
<script>
    function createTrainerCollaboration()
    {
        ajaxcall(window.location.href + "?createTrainerCollaboration=" + document.getElementById("add_trainer_table_hidden").value, "POST").then((jsonresponse) => {
            printdebug("Risposta createTrainerCollaboration: ");
            printdebug(jsonresponse);

            getTrainerCollaborations();
            document.getElementById('new_trainer_collab').style.display='none';

        }, (httpstatus) => {
            printdebug("Errore richiesta: " + httpstatus);
        });
    }

    function selectPossibleNewTrainer(trainer_id)
    {
        //Get hidden field
        let hidden_field = document.getElementById("add_trainer_table_hidden");
        //Get submit button
        let submit_button = document.getElementById("add_trainer_table_submit");
        //Get trainer selected
        let selected_trainer = document.getElementById(trainer_id);
        //If the new trainer is the old one
        if(hidden_field.value === trainer_id.replace("trainer_id_", ""))
        {
            //Changing hidden field value
            hidden_field.value = "";
            //Disabilitate the subimt button
            submit_button.disabled = true;
            //Removing w3-theme from the trainer
            selected_trainer.classList.remove("w3-theme");
        }
        else
        {
            if(hidden_field.value != null)
            {
                //Removing w3-theme from the old trainer
                let old_selected_trainer = document.getElementById("trainer_id_" + hidden_field.value);
                if(old_selected_trainer != null) {
                    old_selected_trainer.classList.remove("w3-theme");
                }
            }

            //Changing hidden field value
            hidden_field.value = trainer_id.replace("trainer_id_", "");

            //Add w3-theme to the new trainer
            selected_trainer.classList.add("w3-theme");

            //Abilitate the subimt button
            submit_button.disabled = false;
        }
    }

    function searchNewTrainer(){
        ajaxcall(window.location.href + "?getNewPossibleTrainer=true").then((jsonresponse) => {
            printdebug("Risposta getNewPossibleTrainer: ");
            printdebug(jsonresponse);
            let mytable = document.getElementById("add_trainer_table");
            let old_tbody = mytable.getElementsByTagName('tbody')[0];
            let tbody = document.createElement('tbody');
            let search_bar = document.getElementById('add_trainer_search_bar');

            if(jsonresponse.length > 0)
            {
                for(let i = 0; i < jsonresponse.length; i++)
                {
                    let tr = document.createElement("tr");

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
                search_bar.disable = false;
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
                search_bar.disabled = true;
            }

            //Replace the old body with the new one
            mytable.replaceChild(tbody, old_tbody);
        }, (httpstatus) => {
            printdebug("Errore richiesta: " + httpstatus);
        });

        document.getElementById("add_trainer_table_hidden").value = "";
        document.getElementById("add_trainer_search_bar").value = "";
        //Show modal form
        document.getElementById('new_trainer_collab').style.display='block';
        //Go to top of the page
        document.getElementById('new_trainer_collab').scrollTo(0, 0);
    }

    function getTrainerCollaborations() {
        ajaxcall(window.location.href + "?getTrainerCollaborations=true").then((jsonresponse) => {
            printdebug("Risposta getTrainerCollaborations: ");
            printdebug(jsonresponse);
            let myothertable = document.getElementById("workout_request");
            let button_new_workout = document.getElementById("workout_request_button_new_workout");
            let button_update_status = document.getElementById("workout_request_button_update_status");
            myothertable.style.visibility = "hidden";
            button_new_workout.style.visibility = "hidden";
            button_update_status.style.visibility = "hidden";

            let mytable = document.getElementById("trainer_collaboration");
            let old_tbody = mytable.getElementsByTagName('tbody')[0];
            let tbody = document.createElement('tbody');

            if(jsonresponse.length > 0)
            {
                for(let i = 0; i < jsonresponse.length; i++)
                {
                    let tr = document.createElement("tr");

                    tr.id = "collaboration_id_" + jsonresponse[i].collaboration_id;

                    let td = document.createElement("td");
                    td.textContent = jsonresponse[i].name + " " + jsonresponse[i].surname;
                    tr.appendChild(td);

                    td = document.createElement("td");
                    if(jsonresponse[i].status === 0)
                    {
                        td.textContent = "Active";
                        td.classList.add("w3-text-green");
                        td.style.fontWeight = "bold";
                        tr.addEventListener("click", getWorkoutRequest.bind(tr, jsonresponse[i]));
                        tr.style.cursor = "pointer";
                    }
                    else if(jsonresponse[i].status === 1)
                    {
                        td.textContent = "Pending approval";
                        tr.style.cursor = "not-allowed";
                    }
                    else if(jsonresponse[i].status === 2)
                    {
                        td.textContent = "Terminated";
                        td.classList.add("w3-text-red");
                        td.style.fontWeight = "bold";
                        tr.addEventListener("click", getWorkoutRequest.bind(tr, jsonresponse[i]));
                        tr.style.cursor = "pointer";
                    }

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
                td.colSpan = 2;
                tr.appendChild(td);
                tbody.appendChild(tr);
            }

            //Replace the old body with the new one
            mytable.replaceChild(tbody, old_tbody);
        }, (httpstatus) => {
            printdebug("Errore richiesta: " + httpstatus);
        });
    }

    function getWorkoutRequest(value) {
        if(value !== undefined && value.collaboration_id != null)
            document.getElementById("workout_request_hidden_field").value = value.collaboration_id;

        let collaboration_id = document.getElementById("workout_request_hidden_field").value;

        if(collaboration_id != null && collaboration_id !== "")
        {
            ajaxcall(window.location.href + "?getWorkoutRequest=" + collaboration_id).then((jsonresponse) => {
                printdebug("Risposta getWorkoutRequest: ");
                printdebug(jsonresponse);

                let mytable = document.getElementById("workout_request");
                document.getElementById("new_workout_hidden_field").value = collaboration_id;
                let old_tbody = mytable.getElementsByTagName('tbody')[0];
                let tbody = document.createElement('tbody');

                if(jsonresponse.length > 0)
                {
                    for(let i = 0; i < jsonresponse.length; i++)
                    {
                        let tr = document.createElement("tr");
                        tr.id = "workout_request_id_ " + jsonresponse[i].request_id;

                        let td = document.createElement("td");
                        td.textContent = jsonresponse[i].request_date;
                        tr.appendChild(td);

                        td = document.createElement("td");
                        td.textContent = jsonresponse[i].workout_goals;
                        tr.appendChild(td);

                        td = document.createElement("td");
                        td.textContent = jsonresponse[i].workout_days;
                        tr.appendChild(td);

                        td = document.createElement("td");
                        if(jsonresponse[i].response === true)
                        {
                            td.classList.add("w3-text-green");
                            td.style.fontWeight = "bold";

                            let a = document.createElement('a');
                            a.href = "?getWorkoutResponse=" + jsonresponse[i].request_id;
                            a.textContent = "Download Workout";
                            td.appendChild(a);
                        }
                        else
                        {
                            td.textContent = "Requested";
                        }
                        tr.appendChild(td);

                        tbody.appendChild(tr);
                    }
                }
                else
                {
                    let tr = document.createElement("tr");
                    let td = document.createElement("td");
                    td.classList.add("w3-center");
                    td.textContent = "No workout request!";
                    td.colSpan = 3;
                    tr.appendChild(td);
                    tbody.appendChild(tr);
                }

                //Replace the old body with the new one
                mytable.replaceChild(tbody, old_tbody);

                //Showing the table
                mytable.style.visibility = "visible";

                //Showing the buttons
                document.getElementById("workout_request_button_update_status").style.visibility = "visible";
                if(value !== undefined && value.status === 0) //If the collaboration is active
                    document.getElementById("workout_request_button_new_workout").style.visibility = "visible";
            }, (httpstatus) => {
                printdebug("Errore richiesta: " + httpstatus);
            });
        }
    }


    function newWorkoutRequestForm(){
        //Clear fields
        document.getElementById('new_workout_workout_goal').value = "";
        document.getElementById('new_workout_workout_days').value = 3;
        document.getElementById('new_workout_health_notes').value = "";

        //Show modal form
        //document.getElementById("add_trainer_table_hidden").value = "";
        document.getElementById('new_workout').style.display='block';

        //Go to top of the page
        document.getElementById('new_workout').scrollTo(0, 0);
    }

    function newWorkoutRequest()
    {
        let data = {
            'workout_goal': document.getElementById("new_workout_workout_goal").value,
            'workout_days': document.getElementById("new_workout_workout_days").value,
            'health_notes': document.getElementById("new_workout_health_notes").value
        };

        ajaxcall(window.location.href + "?createWorkoutRequest=" + document.getElementById("new_workout_hidden_field").value, "POST", data).then((jsonresponse) => {
            printdebug("Risposta newWorkoutRequest: ");
            printdebug(jsonresponse);

            //Update Table
            getWorkoutRequest();
            //Closing modal
            document.getElementById('new_workout').style.display='none';

        }, (httpstatus) => {
            printdebug("Errore richiesta: " + httpstatus);
        });

        return false;
    }
</script>
