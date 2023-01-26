<h3 class=""><b>My nutritionist collaboration</b></h3>
<button onclick="getNutritionistCollaborations();" class="w3-button w3-theme-dark w3-ripple w3-medium w3-margin-bottom w3-right">Update Status</button>
<button onclick="searchNewNutritionist();" class="w3-button w3-theme w3-ripple w3-right w3-medium w3-margin-bottom w3-margin-right">New Nutritionist</button>
<table class="w3-table-all w3-hoverable" id="nutritionist_collaboration">
    <thead>
    <tr class="w3-theme">
        <th>Nutritionist Name</th>
        <th>Request Status</th>
    </tr>
    </thead>
    <tbody>
    </tbody>
</table>
<br/><br/>
<button onclick="getDietRequest();" id="diet_request_button_update_status" class="w3-button w3-theme-dark w3-ripple w3-medium w3-margin-bottom w3-right" style="visibility : hidden">Update Status</button>
<button onclick="newDietRequestForm();" id="diet_request_button_new_diet" class="w3-button w3-theme w3-ripple w3-right w3-medium w3-margin-bottom w3-margin-right" style="visibility : hidden">New Diet</button>
<input type="hidden" id="diet_request_hidden_field" name="new_diet_hidden_field" value="">
<table class="w3-table-all w3-margin-bottom" id="diet_request" style="visibility : hidden">
    <thead>
    <tr class="w3-theme">
        <th>Request Date</th>
        <th>Diet Goals</th>
        <th>Lifestyle</th>
        <th>Status</th>
    </tr>
    </thead>
    <tbody>
    </tbody>
</table>
<!------------------------- MODAL ------------------------------------->
<div id="new_nutritionist_collab" class="w3-modal">
    <div class="w3-modal-content w3-card-4 w3-animate-zoom w3-margin-bottom" style="max-width:600px">
        <div class="w3-center">
            <br/>
            <h3 class="w3-text-theme"><b>Add new Nutritionist</b></h3>
            <span onclick="document.getElementById('new_nutritionist_collab').style.display='none'" class="w3-button w3-xlarge w3-hover-red w3-theme w3-display-topright" title="Close Modal">&times;</span>
        </div>
        <br><br>
        <div class="w3-margin">
            <input class="w3-input w3-border w3-padding" type="text" placeholder="Search for names.." id="add_nutritionist_search_bar" onkeyup="filter(this);">
            <table class="w3-table-all w3-margin-top" id="add_nutritionist_table">
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
            <input type="hidden" id="add_nutritionist_table_hidden" name="add_nutritionist_table_hidden" value="">
            <button id="add_nutritionist_table_submit" class="w3-button w3-block w3-theme w3-section w3-padding" disabled type="button" onclick="createNutritionistCollaboration()">Request collaboration</button>
        </div>
        <br>
    </div>
</div>
<div id="new_diet" class="w3-modal">
    <div class="w3-modal-content w3-card-4 w3-animate-zoom w3-margin-bottom" style="max-width:600px">
        <div class="w3-center">
            <br>
            <h3 class="w3-text-theme"><b>New Diet Request</b></h3>
            <span onclick="document.getElementById('new_diet').style.display='none'" class="w3-button w3-xlarge w3-hover-red w3-theme w3-display-topright" title="Close Modal">&times;</span>
        </div>
        <form class="w3-container" action="" method="get" onsubmit="return newDietRequest();">
            <br><br>
            <input type="hidden" id="new_diet_hidden_field" name="new_diet_hidden_field" value="">
            <label for="new_diet_diet_goal"><b>Diet goal</b></label>
            <textarea class="w3-input w3-border w3-margin-bottom" style="resize:none;" name="diet_goal" id="new_diet_diet_goal" required ></textarea>
            <label for="new_diet_allergies"><b>Allergies</b></label>
            <textarea class="w3-input w3-border w3-margin-bottom" style="resize:none;" name="allergies" id="new_diet_allergies" required ></textarea>
            <label for="new_diet_intolerances"><b>Intolerances</b></label>
            <textarea class="w3-input w3-border w3-margin-bottom" style="resize:none;" name="intolerances" id="new_diet_intolerances" required ></textarea>
            <label for="new_diet_basal_metabolic_rate"><b>Basal Metabolic Rate</b></label>
            <input class="w3-input w3-border w3-margin-bottom" type="number" name="basal_metabolic_rate" required id="new_diet_basal_metabolic_rate" value="1000" min="1" max="5000" step="1" />
            <label for="new_diet_lifestyle"><b>Lifestyle</b> (where 1 is sedentary and 5 is extremely active)</label>
            <input class="w3-input w3-border w3-margin-bottom" type="number" name="lifestyle" required id="new_diet_lifestyle" value="3" min="1" max="5" step="1" />
            <button class="w3-button w3-block w3-theme w3-section w3-padding" type="submit" >Request Diet</button>
        </form>
    </div>
</div>
<!------------------------- SCRIPTS ------------------------------------->
<script>
    function createNutritionistCollaboration()
    {
        let hidden_field = document.getElementById("add_nutritionist_table_hidden");
        ajaxcall(window.location.href + "?createNutritionistCollaboration=" + hidden_field.value).then((jsonresponse) => {
            printdebug("Risposta createNutritionistCollaboration: ");
            printdebug(jsonresponse);

            getNutritionistCollaborations();
            document.getElementById('new_nutritionist_collab').style.display='none';

        }, (httpstatus) => {
            printdebug("Errore richiesta: " + httpstatus);
        });
    }

    function selectPossibleNewNutritionist(nutritionist_id)
    {
        //Get hidden field
        let hidden_field = document.getElementById("add_nutritionist_table_hidden");
        //Get submit button
        let submit_button = document.getElementById("add_nutritionist_table_submit");
        //Get nutritionist selected
        let selected_nutritionist = document.getElementById(nutritionist_id);
        //If the new nutritionist is the old one
        if(hidden_field.value === nutritionist_id.replace("nutritionist_id_", ""))
        {
            //Changing hidden field value
            hidden_field.value = "";
            //Disabilitate the subimt button
            submit_button.disabled = true;
            //Removing w3-theme from the nutritionist
            selected_nutritionist.classList.remove("w3-theme");
        }
        else
        {
            if(hidden_field.value != null)
            {
                //Removing w3-theme from the old nutritionist
                let old_selected_nutritionist = document.getElementById("nutritionist_id_" + hidden_field.value);
                if(old_selected_nutritionist != null) {
                    old_selected_nutritionist.classList.remove("w3-theme");
                }
            }

            //Changing hidden field value
            hidden_field.value = nutritionist_id.replace("nutritionist_id_", "");

            //Add w3-theme to the new nutritionist
            selected_nutritionist.classList.add("w3-theme");

            //Abilitate the subimt button
            submit_button.disabled = false;
        }
    }

    function searchNewNutritionist(){
        ajaxcall(window.location.href + "?getNewPossibleNutritionist=true").then((jsonresponse) => {
            printdebug("Risposta getNewPossibleNutritionist: ");
            printdebug(jsonresponse);
            let mytable = document.getElementById("add_nutritionist_table");
            let old_tbody = mytable.getElementsByTagName('tbody')[0];
            let tbody = document.createElement('tbody');
            let search_bar = document.getElementById('add_nutritionist_search_bar');

            if(jsonresponse.length > 0)
            {
                for(let i = 0; i < jsonresponse.length; i++)
                {
                    let tr = document.createElement("tr");

                    tr.id = "nutritionist_id_" + jsonresponse[i].professional_id;
                    tr.addEventListener("click", selectPossibleNewNutritionist.bind(tr, tr.id));
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
                td.textContent = "No nutritionists to add!";
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

        document.getElementById("add_nutritionist_table_hidden").value = "";
        document.getElementById("add_nutritionist_search_bar").value = "";
        //Show modal form
        document.getElementById('new_nutritionist_collab').style.display='block';
        //Go to top of the page
        document.getElementById('new_nutritionist_collab').scrollTo(0, 0);
    }

    function getNutritionistCollaborations() {
        ajaxcall(window.location.href + "?getNutritionistCollaborations=true").then((jsonresponse) => {
            printdebug("Risposta getNutritionistCollaborations: ");
            printdebug(jsonresponse);
            let myothertable = document.getElementById("diet_request");
            let button_new_diet = document.getElementById("diet_request_button_new_diet");
            let button_update_status = document.getElementById("diet_request_button_update_status");
            myothertable.style.visibility = "hidden";
            button_new_diet.style.visibility = "hidden";
            button_update_status.style.visibility = "hidden";

            let mytable = document.getElementById("nutritionist_collaboration");
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
                    if(jsonresponse[i].status === true)
                    {
                        td.textContent = "Active";
                        td.classList.add("w3-text-green");
                        td.style.fontWeight = "bold";
                        tr.addEventListener("click", getDietRequest.bind(tr, jsonresponse[i].collaboration_id));
                        tr.style.cursor = "pointer";
                    }
                    else
                    {
                        td.textContent = "Pending approval";
                        tr.style.cursor = "not-allowed";
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

    function getDietRequest(value) {
        if(value != null)
            document.getElementById("diet_request_hidden_field").value = value;

        let collaboration_id = document.getElementById("diet_request_hidden_field").value;

        if(collaboration_id != null && collaboration_id !== "")
        {
            ajaxcall(window.location.href + "?getDietRequest=" + collaboration_id).then((jsonresponse) => {
                printdebug("Risposta getDietRequest: ");
                printdebug(jsonresponse);

                let mytable = document.getElementById("diet_request");
                document.getElementById("new_diet_hidden_field").value = collaboration_id;
                let old_tbody = mytable.getElementsByTagName('tbody')[0];
                let tbody = document.createElement('tbody');

                if(jsonresponse.length > 0)
                {
                    for(let i = 0; i < jsonresponse.length; i++)
                    {
                        let tr = document.createElement("tr");
                        tr.id = "diet_request_id_ " + jsonresponse[i].request_id;

                        let td = document.createElement("td");
                        td.textContent = jsonresponse[i].request_date;
                        tr.appendChild(td);

                        td = document.createElement("td");
                        td.textContent = jsonresponse[i].diet_goals;
                        tr.appendChild(td);

                        td = document.createElement("td");
                        td.textContent = jsonresponse[i].lifestyle;
                        tr.appendChild(td);

                        td = document.createElement("td");
                        if(jsonresponse[i].response === true)
                        {
                            td.classList.add("w3-text-green");
                            td.style.fontWeight = "bold";

                            let a = document.createElement('a');
                            a.href = "?getDietResponse=" + jsonresponse[i].request_id;
                            a.textContent = "Download Diet";
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
                    td.textContent = "No diet request yet!";
                    td.colSpan = 3;
                    tr.appendChild(td);
                    tbody.appendChild(tr);
                }

                //Replace the old body with the new one
                mytable.replaceChild(tbody, old_tbody);

                //Showing the table
                mytable.style.visibility = "visible";

                //Showing the buttons
                document.getElementById("diet_request_button_update_status").style.visibility = "visible";
                document.getElementById("diet_request_button_new_diet").style.visibility = "visible";
            }, (httpstatus) => {
                printdebug("Errore richiesta: " + httpstatus);
            });
        }
    }


    function newDietRequestForm(){
        //Clear fields
        document.getElementById('new_diet_diet_goal').value = "";
        document.getElementById('new_diet_allergies').value = "";
        document.getElementById('new_diet_intolerances').value = "";
        document.getElementById('new_diet_basal_metabolic_rate').value = 1000;
        document.getElementById('new_diet_lifestyle').value = 3;

        //Show modal form
        //document.getElementById("add_nutritionist_table_hidden").value = "";
        document.getElementById('new_diet').style.display='block';

        //Go to top of the page
        document.getElementById('new_diet').scrollTo(0, 0);
    }

    function newDietRequest()
    {
        let data = {
            'diet_goal': document.getElementById("new_diet_diet_goal").value,
            'allergies': document.getElementById("new_diet_allergies").value,
            'intolerances': document.getElementById("new_diet_intolerances").value,
            'basal_metabolic_rate': document.getElementById("new_diet_basal_metabolic_rate").value,
            'lifestyle': document.getElementById("new_diet_lifestyle").value
        };

        ajaxcall(window.location.href + "?createDietRequest=" + document.getElementById("new_diet_hidden_field").value, "POST", data).then((jsonresponse) => {
            printdebug("Risposta newDietRequest: ");
            printdebug(jsonresponse);

            //Update Table
            getDietRequest();
            //Closing modal
            document.getElementById('new_diet').style.display='none';

        }, (httpstatus) => {
            printdebug("Errore richiesta: " + httpstatus);
        });

        return false;
    }
</script>
