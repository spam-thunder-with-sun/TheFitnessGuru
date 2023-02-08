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
    <div class="w3-modal-content w3-card-4 w3-animate-zoom w3-margin-bottom" style="width:800px;">
        <div class="w3-center">
            </br>
            <h3 class="w3-text-theme"><b>Workout Info</b></h3>
            <span onclick="document.getElementById('new_workout').style.display='none'" class="w3-button w3-xlarge w3-hover-red w3-theme w3-display-topright" title="Close Modal">&times;</span>
        </div>
        <div class="w3-container">
            <h5><b class="w3-text-theme" >Athlete Full Name: </b><span id="new_workout_athlete_name"></span></h5>
            <h5><b class="w3-text-theme" >Goals: </b><span id="new_workout_goals"></span></h5>
            <h5><b class="w3-text-theme" >Health Notes: </b><span id="new_workout_health_notes"></span></h5>
            <h5><b class="w3-text-theme" >Workout Days: </b><span id="new_workout_days"></span></h5>
            <input type="hidden" id="new_workout_request_id">
        </div>
        <div class="w3-center">
            </br>
            <h3 class="w3-text-theme"><b>Workout Creation</b></h3>
        </div>
        <form class="w3-container" onsubmit="return sendNewWorkout();" onkeydown="return event.key != 'Enter';">
            <label for="new_workout_name" class="w3-text-theme"><h5 style="margin-bottom: 0"><b>Workout Name: </b></h5></label>
            <input class="w3-input w3-border w3-padding w3-margin-bottom" type="text" id="new_workout_name" required>
            <label class="w3-text-theme"><h5 style="margin-bottom: 0"><b>Search some exercises: </b></h5></label>
            <div class="w3-container w3-border w3-border-theme">
                <label for="exercise_type" class="w3-text-theme"><h5 style="margin-bottom: 0"><b>Exercise type:</b></h5></label>
                <select class="w3-select w3-margin-bottom w3-border w3-input w3-padding" id="exercise_type" onchange="getExercises();">
                    <option value="" selected>None</option>
                    <option value="cardio">Cardio</option>
                    <option value="olympic_weightlifting">Olympic Weightlifting</option>
                    <option value="plyometrics">Plyometrics</option>
                    <option value="powerlifting">Powerlifting</option>
                    <option value="strength">Strength</option>
                    <option value="stretching">Stretching</option>
                    <option value="strongman">Strongman</option>
                </select>
                <label for="exercise_muscle" class="w3-text-theme"><h5 style="margin-bottom: 0"><b>Exercise muscle:</b></h5></label>
                <select class="w3-select w3-margin-bottom w3-border w3-input w3-padding" id="exercise_muscle" onchange="getExercises();">
                    <option value="" selected>None</option>
                    <option value="abdominals">Abdominals</option>
                    <option value="abductors">Abductors</option>
                    <option value="adductors">Adductors</option>
                    <option value="biceps">Biceps</option>
                    <option value="calves">Calves</option>
                    <option value="chest">Chest</option>
                    <option value="glutes">Glutes</option>
                    <option value="hamstrings">Hamstrings</option>
                    <option value="lats">Lats</option>
                    <option value="lower_back">Lower_back</option>
                    <option value="middle_back">Middle_back</option>
                    <option value="neck">Neck</option>
                    <option value="quadriceps">Quadriceps</option>
                    <option value="traps">Traps</option>
                    <option value="triceps">Triceps</option>
                </select>
                <label for="exercise_difficulty" class="w3-text-theme"><h5 style="margin-bottom: 0"><b>Exercise difficulty:</b></h5></label>
                <select class="w3-select w3-margin-bottom w3-border w3-input w3-padding" id="exercise_difficulty" onchange="getExercises();">
                    <option value="" selected>None</option>
                    <option value="beginner">Beginner</option>
                    <option value="intermediate">Intermediate</option>
                    <option value="expert">Expert</option>
                </select>
                <label for="exercise_search_bar" class="w3-text-theme"><h5 style="margin-bottom: 0"><b>Exercise name: </b></h5></label>
                <div class="w3-display-container">
                    <i class="fas fa-search w3-display-right w3-margin-right w3-text-theme fa-lg" style="cursor: pointer" onclick="getExercises()"></i>
                    <input class="w3-input w3-border w3-padding w3-margin-bottom" style="width: 90%" type="text" placeholder="Search for exercise name..." id="exercise_search_bar" onkeydown="getExercises(event.key);">
                </div>
            </div>
            <label for="exercise_list" class="w3-text-theme"><h5 style="margin-bottom: 0"><b>Add exercises: </b></h5></label>
            <ul class="w3-ul w3-hoverable w3-margin-bottom w3-border" id="exercise_list">
                <li>No exercises</li>
            </ul>
            <label for="exercise_day_input" class="w3-text-theme"><h5 style="margin-bottom: 0"><b>Select day: </b></h5></label>
            <select class="w3-select w3-margin-bottom w3-border w3-input w3-padding" name="day_input" id="exercise_day_input" onchange="">

            </select>
            <label for="exercise_overview" class="w3-text-theme"><h5 style="margin-bottom: 0"><b>Overview: </b></h5></label>
            </br>
            <span id="exercise_overview"></span>
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
        document.getElementById("new_workout_health_notes").textContent = allData.health_notes;
        document.getElementById("new_workout_days").textContent = allData.workout_days;
        document.getElementById("new_workout_goals").textContent = allData.workout_goals;

        //Clear data
        document.getElementById("exercise_search_bar").value = "";
        document.getElementById("exercise_type").selectedIndex = 0;
        document.getElementById("exercise_muscle").selectedIndex = 0;
        document.getElementById("exercise_difficulty").selectedIndex = 0;
        let exercise_list = document.getElementById("exercise_list");
        exercise_list.innerHTML = "";
        let li = document.createElement("li");
        li.textContent = "No exercise";
        exercise_list.appendChild(li);
        document.getElementById("new_workout_name").value = "";

        //-------------------------------------------

        let day_input = document.getElementById("exercise_day_input");
        day_input.innerHTML = "";
        for(let i = 1; i <= allData.workout_days; i++)
        {
            let option = document.createElement("option");
            option.text = "Day " + i;
            option.value = i + "";

            day_input.add(option);
        }
        day_input.selectedIndex = 0;

        let exercise_overview = document.getElementById("exercise_overview");
        exercise_overview.innerHTML = "";
        for(let i = 1; i <= allData.workout_days; i++)
        {
            let label = document.createElement("label");
            label.textContent = "Day " + i + " workout:";
            label.classList.add("w3-text-theme");

            let ul = document.createElement("ul");
            ul.id = "workout_day_" + i;
            ul.classList.add("w3-ul");
            ul.classList.add("w3-margin-bottom");

            exercise_overview.
            appendChild(label);
            exercise_overview.appendChild(ul);
        }

        //Show modal form
        document.getElementById('new_workout').style.display='block';

        //Go to top of the page
        document.getElementById('new_workout').scrollTo(0, 0);
    }

    function sendNewWorkout()
    {
        let days = +document.getElementById("new_workout_days").textContent;
        console.log("Qui");

        let exerciseList = [];
        for(let i = 1; i <= days; i++)
        {
            let day_list = [];
            let listItems = document.getElementById("workout_day_" + i).getElementsByTagName("li");

            for (let y = 0; y < listItems.length; y++)
            {
                let li = listItems[y];
                let exercise_data = li.getElementsByTagName("input");
                let sets = exercise_data[0].value;
                let reps = exercise_data[1].value;
                let rest = exercise_data[2].value;

                day_list.push({
                    'name': li.getAttribute('name'),
                    'muscle' : li.getAttribute('muscle'),
                    'instructions' : li.getAttribute('instructions'),
                    'equipment' : li.getAttribute('equipment'),
                    'difficulty' : li.getAttribute('difficulty'),
                    'type' : li.getAttribute('type'),
                    'sets' : sets,
                    'reps' : reps,
                    'rest' : rest,
                })
            }
            exerciseList.push({
                'name': 'day_' + i,
                'exercises': day_list
            })
        }

        let data = {
            'name': document.getElementById("new_workout_name").value,
            'health_notes': document.getElementById("new_workout_health_notes").textContent,
            'goals': document.getElementById("new_workout_goals").textContent,
            'day': document.getElementById("new_workout_days").textContent,
            'athlete_full_name' : document.getElementById("new_workout_athlete_name").textContent,
            'days': exerciseList
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

    function getExercises(keyPressed)
    {
        if(keyPressed === undefined || keyPressed === 'Enter')
        {
            let exerciseParameters = "";
            let exerciseType = document.getElementById("exercise_type").value;
            let exerciseMuscle = document.getElementById("exercise_muscle").value;
            let exerciseDifficulty = document.getElementById("exercise_difficulty").value;
            let exerciseName = document.getElementById("exercise_search_bar").value.trim().toLowerCase();

            if(exerciseType != "")
                exerciseParameters += "type=" + exerciseType + "&";

            if(exerciseMuscle != "")
                exerciseParameters += "muscle=" + exerciseMuscle + "&";

            if(exerciseDifficulty != "")
                exerciseParameters += "Difficulty=" + exerciseDifficulty + "&";

            if(exerciseName != "")
                exerciseParameters += "name=" + exerciseName + "&";

            if(exerciseParameters.length > 0)
            {
                let exercise_list = document.getElementById("exercise_list");

                //Empty list
                exercise_list.innerHTML = "";
                let li = document.createElement("li");
                li.innerHTML = 'Loading... <i class="fa fa-spinner fa-spin w3-text-theme"></i>';
                exercise_list.appendChild(li);
                ajaxcall(window.location.href + "/WorkoutAdapter?" + exerciseParameters).then((jsonresponse) => {
                    printdebug("Risposta getExercises [NEW]: ");
                    printdebug(jsonresponse);
                    jsonresponse = JSON.parse(jsonresponse);

                    //Empty list
                    exercise_list.innerHTML = "";

                    if(jsonresponse.length > 0)
                    {
                        for(let i = 0; i < jsonresponse.length; i++)
                        {
                            let li = document.createElement("li");
                            li.textContent = jsonresponse[i].name;
                            li.addEventListener("click", addExercise.bind(li, jsonresponse[i]));
                            li.style.cursor = "pointer";
                            //li.classList.add("w3-hover-text-theme");
                            li.classList.add("w3-ripple");
                            li.classList.add("w3-hover-theme");
                            //console.log(i + "li")
                            exercise_list.appendChild(li);
                        }
                    }
                    else
                    {
                        let li = document.createElement("li");
                        li.textContent = "No exercise";
                        exercise_list.appendChild(li);
                    }
                }, (httpstatus) => {
                    printdebug("Errore richiesta: " + httpstatus);
                });
            }
        }
    }


    function addExercise(exercise)
    {
        let day_input = document.getElementById("exercise_day_input").value;
        let list = document.getElementById("workout_day_" + day_input);

        let li = document.createElement("li");
        li.setAttribute('name', exercise.name);
        li.setAttribute('muscle', exercise.muscle);
        li.setAttribute('instructions', exercise.instructions);
        li.setAttribute('equipment', exercise.equipment);
        li.setAttribute('difficulty', exercise.difficulty);
        li.setAttribute('type', exercise.type);

        li.classList.add("w3-bar");
        li.classList.add("w3-border-0");
        li.classList.add("w3-margin-bottom");
        li.style.padding = "0px";
        let cross = '<span onclick="this.parentElement.parentElement.removeChild(this.parentElement)" class="w3-button w3-margin-left w3-right w3-hover-red w3-text-theme"><b>&times;</b></span>';
        let content = '<span class="w3-left" style="padding: 8px">' + exercise.name +'</span>';
        let reps = '<input id="' + '" class="w3-input w3-right w3-border w3-border-theme" type="number" placeholder="Reps" min="1" style="width: 100px" required>';
        let rest = '<input id="' + '" class="w3-input w3-right w3-border w3-border-theme w3-margin-left" type="number" placeholder="Rest" min="0" style="width: 100px" required>';
        let sets = '<input id="' + '" class="w3-input w3-right w3-border w3-border-theme w3-margin-left" type="number" placeholder="Sets" min="1" style="width: 100px" required>';

        li.innerHTML = content + cross + sets + rest + reps;
        list.appendChild(li);
    }
</script>