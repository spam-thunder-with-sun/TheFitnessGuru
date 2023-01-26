<h3 class=""><b>Workout Request</b></h3>
<button onclick="getWorkoutRequest();" class="w3-button w3-theme-dark w3-ripple w3-medium w3-margin-bottom w3-right">Update Status</button>
<table class="w3-table-all w3-hoverable" id="workout_request">
    <thead>
    <tr class="w3-theme">
        <th>Athlete Name</th>
        <th>Request Date</th>
        <th>Request Status</th>
    </tr>
    </thead>
    <tbody>
    </tbody>
</table>
<br><br>
<div id="new_workout" class="w3-modal">
    <div class="w3-modal-content w3-card-4 w3-animate-zoom w3-margin-bottom" style="max-width:800px">
        <div class="w3-center">
            </br>
            <h3 class="w3-text-theme"><b>Workout Info</b></h3>
            <span onclick="document.getElementById('new_workout').style.display='none'" class="w3-button w3-xlarge w3-hover-red w3-theme w3-display-topright" title="Close Modal">&times;</span>
        </div>
        <div class="w3-container">
            <h5><b class="w3-text-theme" >Athlete Full Name: </b><span id="new_workout_athlete_name"></span></h5>
            <h5><b class="w3-text-theme" >Allergies: </b><span id="new_workout_allergies"></span></h5>
            <h5><b class="w3-text-theme" >Intolerances: </b><span id="new_workout_intolerances"></span></h5>
            <h5><b class="w3-text-theme" >Basal Metabolic Rate: </b><span id="new_workout_bmr"></span></h5>
            <h5><b class="w3-text-theme" >Goals: </b><span id="new_workout_goals"></span></h5>
            <h5><b class="w3-text-theme" >Lifestyle: </b><span id="new_workout_lifestyle"></span></h5>
            <input type="hidden" id="new_workout_request_id">
        </div>
        <div class="w3-center">
            </br>
            <h3 class="w3-text-theme"><b>Workout Creation</b></h3>
        </div>
        <form class="w3-container" onsubmit="return sendNewWorkout();" onkeydown="return event.key != 'Enter';">
            <label for="new_workout_name" class="w3-text-theme"><h5 style="margin-bottom: 0"><b>Workout Name: </b></h5></label>
            <input class="w3-input w3-border w3-padding w3-margin-bottom" type="text" id="new_workout_name" required>
            <label for="recipe_search_bar" class="w3-text-theme"><h5 style="margin-bottom: 0"><b>Search some recipes: </b></h5></label>
            <div class="w3-display-container">
                <i class="fas fa-search w3-display-right w3-margin-right w3-text-theme fa-lg" style="cursor: pointer" onclick="getRecipes()"></i>
                <input class="w3-input w3-border w3-padding w3-margin-bottom" style="width: 90%" type="text" placeholder="Search for recipe..." id="recipe_search_bar" onkeydown="getRecipes(event.key);">
            </div>
            <label for="recipe_list" class="w3-text-theme"><h5 style="margin-bottom: 0"><b>Add recipes: </b></h5></label>
            <ul class="w3-ul w3-hoverable w3-margin-bottom w3-border" id="recipe_list">
                <li>No recipes</li>
            </ul>
            <label for="recipe_day_input" class="w3-text-theme"><h5 style="margin-bottom: 0"><b>Select day: </b></h5></label>
            <select class="w3-select w3-margin-bottom w3-border w3-input w3-padding" name="day_input" id="recipe_day_input" onchange="">
                <option value="1" selected>Day 1</option>
                <option value="2">Day 2</option>
                <option value="3">Day 3</option>
                <option value="4">Day 4</option>
                <option value="5">Day 5</option>
                <option value="6">Day 6</option>
                <option value="7">Day 7</option>
            </select>
            <label class="w3-text-theme"><h5 style="margin-bottom: 0"><b>Overview: </b></h5></label>
            </br>
            <label for="workout_day_1" class="w3-text-theme">Day 1 workout:</label>
            <ul class="w3-ul w3-margin-bottom" id="workout_day_1"></ul>
            <label for="workout_day_2" class="w3-text-theme">Day 2 workout:</label>
            <ul class="w3-ul w3-margin-bottom" id="workout_day_2"></ul>
            <label for="workout_day_3" class="w3-text-theme">Day 3 workout:</label>
            <ul class="w3-ul w3-margin-bottom" id="workout_day_3"></ul>
            <label for="workout_day_4" class="w3-text-theme">Day 4 workout:</label>
            <ul class="w3-ul w3-margin-bottom" id="workout_day_4"></ul>
            <label for="workout_day_5" class="w3-text-theme">Day 5 workout:</label>
            <ul class="w3-ul w3-margin-bottom" id="workout_day_5"></ul>
            <label for="workout_day_6" class="w3-text-theme">Day 6 workout:</label>
            <ul class="w3-ul w3-margin-bottom" id="workout_day_6"></ul>
            <label for="workout_day_7" class="w3-text-theme">Day 7 workout:</label>
            <ul class="w3-ul w3-margin-bottom" id="workout_day_7"></ul>
            <button class="w3-button w3-block w3-theme w3-section w3-padding" type="submit" value="Proceed">Create New Workout</button>
        </form>
    </div>
</div>
<script>
    function getWorkoutRequest()
    {
        ajaxcall(window.location.href + "?getWorkoutRequest=true").then((jsonresponse) => {
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
                    tr.id = "workout_request_id_ " + jsonresponse[i].request_id;

                    let td = document.createElement("td");
                    td.textContent = jsonresponse[i].athlete_full_name;
                    tr.appendChild(td);

                    td = document.createElement("td");
                    td.textContent = jsonresponse[i].request_date;
                    tr.appendChild(td);

                    td = document.createElement("td");
                    if(jsonresponse[i].response === true)
                    {
                        td.classList.add("w3-text-green");
                        td.style.fontWeight = "bold";

                        let a = document.createElement('a');
                        a.href = "?getWorkoutResponse=" + jsonresponse[i].request_id;
                        a.textContent = "Workout Sent";
                        td.appendChild(a);
                    }
                    else
                    {
                        td.textContent = "Create workout";
                        td.addEventListener("click", createNewWorkout.bind(tr, jsonresponse[i]));
                        td.style.cursor = "pointer";
                        td.style.textDecoration = "underline";
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
                td.textContent = "No workout request yet!";
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

    function createNewWorkout(allData)
    {
        //Fill the workout fields
        document.getElementById("new_workout_request_id").value = allData.request_id;
        document.getElementById("new_workout_athlete_name").textContent = allData.athlete_full_name;
        document.getElementById("new_workout_allergies").textContent = allData.allergies;
        document.getElementById("new_workout_intolerances").textContent = allData.intolerances;
        document.getElementById("new_workout_bmr").textContent = allData.basal_metabolic_rate;
        document.getElementById("new_workout_goals").textContent = allData.workout_goals;
        document.getElementById("new_workout_lifestyle").textContent = allData.lifestyle;

        //Clear data
        document.getElementById("recipe_search_bar").value = "";
        document.getElementById("recipe_day_input").selectedIndex = 0;
        let recipe_list = document.getElementById("recipe_list");
        recipe_list.innerHTML = "";
        let li = document.createElement("li");
        li.textContent = "No recipe";
        recipe_list.appendChild(li);
        for(let i = 1; i <= 7; i++)
            document.getElementById("workout_day_" + i).innerHTML = "";
        document.getElementById("new_workout_name").value = "";

        //Show modal form
        document.getElementById('new_workout').style.display='block';

        //Go to top of the page
        document.getElementById('new_workout').scrollTo(0, 0);
    }

    function sendNewWorkout()
    {
        let recipeList = [];
        for(let i = 1; i <= 7; i++)
        {
            let day_list = [];
            let listItems = document.getElementById("workout_day_" + i).getElementsByTagName("li");

            for (let y = 0; y < listItems.length; y++)
            {
                let li = listItems[y];
                day_list.push({
                    'title': li.getAttribute('title'),
                    'ingredients' : li.getAttribute('ingredients'),
                    'instructions' : li.getAttribute('instructions'),
                    'servings' : li.getAttribute('servings')
                })
            }
            recipeList.push({
                'name': 'day_' + i,
                'recipes': day_list
            })
        }

        let data = {
            'name': document.getElementById("new_workout_name").value,
            'allergies': document.getElementById("new_workout_allergies").textContent,
            'bmr': document.getElementById("new_workout_bmr").textContent,
            'intolerances': document.getElementById("new_workout_intolerances").textContent,
            'goals': document.getElementById("new_workout_goals").textContent,
            'lifestyle': document.getElementById("new_workout_lifestyle").textContent,
            'athlete_full_name' : document.getElementById("new_workout_athlete_name").textContent,
            'days': recipeList
        };
        let request_id = document.getElementById("new_workout_request_id").value;

        ajaxcall(window.location.href + "?createWorkout=" + request_id, "POST", {'data': JSON.stringify(data)}).then((jsonresponse) => {
            printdebug("Risposta createWorkout: ");
            printdebug(jsonresponse);

            //Update data
            getWorkoutRequest();
            //Close modal
            document.getElementById('new_workout').style.display='none';

        }, (httpstatus) => {
            printdebug("Errore richiesta: " + httpstatus);
        });

        return false;
    }

    function getRecipes(keyPressed)
    {
        if(keyPressed === undefined || keyPressed === 'Enter')
        {
            let search_bar = document.getElementById("recipe_search_bar");
            let recipeName = search_bar.value.trim().toLowerCase();

            if(recipeName.length > 0)
            {
                let base_url = "https://api.api-ninjas.com/v1/recipe?query=";
                let recipe_list = document.getElementById("recipe_list");

                //Empty list
                recipe_list.innerHTML = "";
                let li = document.createElement("li");
                li.innerHTML = 'Loading... <i class="fa fa-spinner fa-spin w3-text-theme"></i>';
                recipe_list.appendChild(li);

                ajaxcall(base_url + recipeName, "GET", null, {'header': 'x-api-key', 'value': 'zJRM87GPTK87aIS0eC41lQ==yT387tRyKsu98tkd'}).then((jsonresponse) => {
                    printdebug("Risposta getRecipes: ");
                    printdebug(jsonresponse);

                    //Empty list
                    recipe_list.innerHTML = "";

                    if(jsonresponse.length > 0)
                    {
                        console.log("Qui!");
                        for(let i = 0; i < jsonresponse.length; i++)
                        {
                            let li = document.createElement("li");
                            li.textContent = jsonresponse[i].title;
                            li.addEventListener("click", addRecipe.bind(li, jsonresponse[i]));
                            li.style.cursor = "pointer";
                            //li.classList.add("w3-hover-text-theme");
                            li.classList.add("w3-ripple");
                            li.classList.add("w3-hover-theme");
                            recipe_list.appendChild(li);
                        }
                    }
                    else
                    {
                        let li = document.createElement("li");
                        li.textContent = "No recipe";
                        recipe_list.appendChild(li);
                    }
                }, (httpstatus) => {
                    printdebug("Errore richiesta: " + httpstatus);
                });
            }
        }
    }

    function addRecipe(recipe)
    {
        let day_input = document.getElementById("recipe_day_input").value;
        let list = document.getElementById("workout_day_" + day_input);

        let li = document.createElement("li");
        li.setAttribute('title', recipe.title);
        li.setAttribute('ingredients', recipe.ingredients);
        li.setAttribute('instructions', recipe.instructions);
        li.setAttribute('servings', recipe.servings);

        li.classList.add("w3-display-container");
        li.innerHTML = recipe.title + '<span onclick="this.parentElement.parentElement.removeChild(this.parentElement)" class="w3-button w3-display-right w3-hover-red w3-text-theme"><b>&times;</b></span>';
        list.appendChild(li);
    }
</script>