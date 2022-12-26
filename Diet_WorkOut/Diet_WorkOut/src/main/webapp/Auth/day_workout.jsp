
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script src="trainerScript.js"></script>
<link rel="stylesheet" href="trainerStyle.css">

<h1 style="padding: 10px">Name: ${name} - Goals: ${goals} - Days: ${days}</h1>
<p id="name_hide" hidden>${name}</p>
<p id="day_hide" hidden>${days}</p>
<p id="goals_hide" hidden>${goals}</p>
<div id="search_form">
    <label for="name">Exercise name:</label>
    <input type="text" id="name" name="name">
    <br>
    <label for="type">Type:</label>
    <select name="type" id="type">
        <option value="null"></option>
        <option value="cardio">Cardio</option>
        <option value="olympic_weightlifting">Olympic weightlifting</option>
        <option value="plyometrics">Plyometrics</option>
        <option value="powerlifting">Powerlifting</option>
        <option value="strength">Strength</option>
        <option value="stretching">Stretching</option>
        <option value="strongman">Strongman</option>
    </select>
    <br>
    <label for="muscle">Muscle:</label>
    <select name="muscle" id="muscle">
        <option value="null"></option>
        <option value="abdominals">Abdominals</option>
        <option value="abductors">Abductors</option>
        <option value="adductors">Adductors</option>
        <option value="biceps">Biceps</option>
        <option value="calves">Calves</option>
        <option value="chest">Chest</option>
        <option value="forearms">Forearms</option>
        <option value="glutes">Glutes</option>
        <option value="hamstrings">Hamstrings</option>
        <option value="lats">Lats</option>
        <option value="lower_back">Lower back</option>
        <option value="middle_back">Middleback</option>
        <option value="neck">Neck</option>
        <option value="quadriceps">Quadriceps</option>
        <option value="traps">Traps</option>
        <option value="triceps">Triceps</option>
    </select>
    <br>
    <label for="difficulty">Difficulty:</label>
    <select name="difficulty" id="difficulty">
        <option value="null"></option>
        <option value="beginner">Beginner</option>
        <option value="intermediate">Intermediate</option>
        <option value="expert">Expert</option>
    </select>
    <br><br>
    <button onclick="getExercises()">Get Exercises</button>
</div>
<br><br>
<select id="day_input">
    <% for(int i = 1; i <= (Integer) request.getAttribute("days"); i+=1) { %>
    <option value= "<%=i%>"><%=i%></option>
    <% } %>
</select>
<br><br>
<div id="exercise_list" style="border-style: solid">
</div>
<div id="day_list">
    <% for(int i = 1; i <= (Integer) request.getAttribute("days"); i+=1) { %>
    <div style="border-style: dotted" id="day_<%=i%>">
        <h3>Day's <%=i%> exercises</h3>
    </div>
    <% } %>
</div>
<br><br>
<button onclick="send_json()">Send Workout</button>