const debug = true;

function printdebug(str)
{
    if(debug)
        console.log(str);
}
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
function ajaxcall(url, method = "GET", data = null) {
    return new Promise( (resolve, reject) => {
        const xhttp = new XMLHttpRequest();
        xhttp.open(method, url, true);
        xhttp.responseType = "json";
        xhttp.onreadystatechange = () => {
            if (xhttp.readyState === 4)
                if (xhttp.status === 200)
                {
                    //printdebug("Richiesta ajax ricevuta con successo!");
                    //printdebug(xhttp.response);
                    resolve(xhttp.response);
                } else
                    reject(xhttp.status);
        }
        if(method === "POST")
        {
            xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
            xhttp.send(data);
        } else
            xhttp.send();
    });}

/*
//Costruisco al tabella
const mytable = document.getElementById("mytable");
for(let i = 0; i < 4; i++)
{      let tr = document.createElement("tr");
    for(let j = 0; j < 4; j++)
    {
        let td = document.createElement("td");
        let padding = document.createTextNode("");
        //&nbsp
        td.appendChild(padding);

        //td.classList.add("w3-hover-theme");
        td.classList.add("w3-border");
        td.classList.add("w3-border-theme");
        td.classList.add("w3-quarter");
        td.classList.add("w3-padding-32");
        td.style.height = "100px";
        td.id = String.fromCharCode(65 + j) + (i + 1);
        //td.textContent = "Test";

        td.addEventListener("click", cellOnClick);

        tr.appendChild(td);
    }
    mytable.appendChild(tr);
}*/

/*
    //Imposto il out of focus della formula ed altro
    const myformula = document.getElementById("myformula");
    myformula.addEventListener("blur", formulablur);
    myformula.addEventListener("keyup", formulakeypress);

    //Setto il setInterval per gestire più utenti
    setInterval(() => {
        ajaxcall(window.location.href + "getupdate?version=" + version).then((jsonresponse) => {
            printdebug("Risposta: ");
            printdebug(jsonresponse);

            //Aggiorno le celle
            updatecell(jsonresponse);
        }, (httpstatus) => {
            printdebug("Errore richiesta: " + httpstatus);
        });
    }, 1000);
 */