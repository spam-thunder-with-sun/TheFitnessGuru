// DEBUG VARIABLE
const debug = true;

// DEBUG METHOD TO PRINT
function printdebug(str)
{
    if(debug)
        console.log(str);
}

// STYLE-RELATED METHODS
function w3_open() {
    document.getElementById("mySidebar").style.display = "block";
}

function w3_close() {
    document.getElementById("mySidebar").style.display = "none";
}

function load_subpage(subpage_id) {
    let x = document.getElementsByClassName("subpage");
    for (let i = 0; i < x.length; i++) {
        x[i].style.display = "none";
    }
    document.getElementById(subpage_id).style.display = "block";
}

// METHODS TO FETCH AJAX REQUEST TO THE APIs
function ajaxcall(url, method = "GET", data = null, header = null) {
    return new Promise( (resolve, reject) => {
        const xhttp = new XMLHttpRequest();
        xhttp.open(method, url, true);
        if(header != null)
            xhttp.setRequestHeader(header.header, header.value);
        xhttp.responseType = "json";
        xhttp.onreadystatechange = () => {
            if (xhttp.readyState === 4)
                if (xhttp.status === 200 || xhttp.status === 204)
                {
                    //printdebug("Richiesta ajax ricevuta con successo!");
                    //printdebug(xhttp.response);
                    resolve(xhttp.response);
                } else
                    reject(xhttp.status);
        }
        if(method === "POST")
        {
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
            xhttp.send(xWwwFormUrlencodedPayload(data));
        } else
            xhttp.send();
    });
}

function xWwwFormUrlencodedPayload(data)
{
    let formBody = [];
    for (let property in data) {
        let encodedKey = encodeURIComponent(property);
        let encodedValue = encodeURIComponent(data[property]);
        formBody.push(encodedKey + "=" + encodedValue);
    }
    formBody = formBody.join("&");

    return formBody;
}

// FILTER METHOD (SEARCH BAR)
function filter(elem) {
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

function logoutRequest()
{
    let logoutURL = window.location.href.substring(0, window.location.href.lastIndexOf("home")) + "logout";

    ajaxcall(logoutURL + "?logout=ok").then((jsonresponse) => {
            printdebug("Risposta loginRequest: ");
            printdebug(jsonresponse);

            window.location.href = window.location.href.substring(0, window.location.href.lastIndexOf("home")) + "login";
        }, (httpstatus) => {
            printdebug("Errore login: " + httpstatus);
        });

    return false;
}