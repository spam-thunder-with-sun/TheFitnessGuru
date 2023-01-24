<h3 class=""><b>Diet Request</b></h3>
<button onclick="getDietRequest();" class="w3-button w3-theme-dark w3-ripple w3-medium w3-margin-bottom w3-right">Update Status</button>
<table class="w3-table-all w3-hoverable" id="diet_request">
    <thead>
    <tr class="w3-theme">
        <th>Athlete Name</th>
        <th>Request Status</th>
    </tr>
    </thead>
    <tbody>
    </tbody>
</table>
<script>
    function getDietRequest(value)
    {
        /*
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
        */
    }
</script>