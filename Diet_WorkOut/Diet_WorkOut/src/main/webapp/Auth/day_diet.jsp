
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
<script src="dietScript.js"></script>
<link rel="stylesheet" href="trainerStyle.css">

<h1 style="padding: 10px">Name: ${name} - Goals: ${goals}</h1>
<p id="name_hide" hidden>${name}</p>
<p id="allergies_hide" hidden>${allergies}</p>
<p id="intolerances_hide" hidden>${intolerances}</p>
<p id="goals_hide" hidden>${goals}</p>
<p id="bmr_hide" hidden>${bmr}</p>
<p id="lifestyle_hide" hidden>${lifestyle}</p>
<div id="search_form">
  <label for="name">Recipe name:</label>
  <input type="text" id="name" name="name">
  <br><br>
  <button onclick="getRecipes()">Get Recipes</button>
</div>
<br><br>
<select id="day_input">
  <% for(int i = 1; i <= 7; i+=1) { %>
  <option value= "<%=i%>"><%=i%></option>
  <% } %>
</select>
<br><br>
<div id="recipe_list" style="border-style: solid">
</div>
<div id="day_list">
  <% for(int i = 1; i <= 7; i+=1) { %>
  <div style="border-style: dotted" id="day_<%=i%>">
    <h3>Day's <%=i%> diet</h3>
  </div>
  <% } %>
</div>
<br><br>
<button onclick="send_json()">Send Diet</button>