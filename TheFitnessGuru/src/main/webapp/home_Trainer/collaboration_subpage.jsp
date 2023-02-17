<h3 class=""><b>My athlete collaboration</b></h3>
<button onclick="getAthleteCollaborations();" class="w3-button w3-theme-dark w3-ripple w3-medium w3-margin-bottom w3-right">Update Status</button>
<table class="w3-table-all w3-hoverable" id="athlete_collaboration">
    <thead>
    <tr class="w3-theme">
        <th>Athlete Name</th>
        <th>Request Status</th>
        <th>Possible action</th>
    </tr>
    </thead>
    <tbody>
    </tbody>
</table>

<script>
    function getAthleteCollaborations()
    {
        ajaxcall(window.location.href + "?getAthleteCollaborations=true").then((jsonresponse) => {
            printdebug("Risposta getAthleteCollaborations: ");
            printdebug(jsonresponse);

            let mytable = document.getElementById("athlete_collaboration");
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
                        tr.appendChild(td);

                        td = document.createElement("td");
                        td.textContent = "Terminate";
                        td.style.textDecoration = "underline";
                        td.style.cursor = "pointer";
                        td.addEventListener("click", terminateCollaboration.bind(tr, jsonresponse[i].collaboration_id));
                        tr.appendChild(td);
                    }
                    else if(jsonresponse[i].status === 1)
                    {
                        td.textContent = "Pending approval";
                        tr.appendChild(td);

                        td = document.createElement("td");
                        td.textContent = "Accept";
                        td.addEventListener("click", acceptCollaboration.bind(tr, jsonresponse[i].collaboration_id));
                        td.style.cursor = "pointer";
                        td.style.textDecoration = "underline";
                        tr.appendChild(td);

                    }
                    else if(jsonresponse[i].status === 2)
                    {
                        td.textContent = "Terminated";
                        td.classList.add("w3-text-red");
                        td.style.fontWeight = "bold";
                        tr.appendChild(td);

                        td = document.createElement("td");
                        td.textContent = "Reactivate";
                        td.addEventListener("click", acceptCollaboration.bind(tr, jsonresponse[i].collaboration_id));
                        td.style.cursor = "pointer";
                        td.style.textDecoration = "underline";
                        tr.appendChild(td);
                    }

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

    function acceptCollaboration(collaboration_id)
    {
        ajaxcall(window.location.href + "?acceptCollaboration=" + collaboration_id, "POST").then((jsonresponse) => {
            printdebug("Risposta acceptCollaboration: ");
            printdebug(jsonresponse);

            getAthleteCollaborations();
        }, (httpstatus) => {
            printdebug("Errore richiesta: " + httpstatus);
        });
    }

    function terminateCollaboration(collaboration_id)
    {
        ajaxcall(window.location.href + "?terminateCollaboration=" + collaboration_id, "POST").then((jsonresponse) => {
            printdebug("Risposta terminateCollaboration: ");
            printdebug(jsonresponse);

            getAthleteCollaborations();
        }, (httpstatus) => {
            printdebug("Errore richiesta: " + httpstatus);
        });
    }
</script>
