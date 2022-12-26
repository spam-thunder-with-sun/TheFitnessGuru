<form action="create-diet-days">
  <label for="name">Diet name:</label>
  <br>
  <input type="text" id="name" name="name" required>
  <br><br>
  <label for="allergies">Allergies</label>
  <br>
  <input type="text" id="allergies" name="allergies" maxlength="200" required>
  <br><br>
  <label for="intolerances">Intolerances</label>
  <br>
  <input type="text" id="intolerances" name="intolerances" maxlength="200" required>
  <br><br>
  <label for="bmr">Basal Metabolic Rate</label>
  <br>
  <input type="number" id="bmr" name="bmr" required>
  <br><br>
  <label for="goals">Goals</label>
  <br>
  <input type="text" id="goals" name="goals" maxlength="300" required>
  <br><br>
  <label for="lifestyle">Lifestyle</label>
  <br>
  <input type="number" id="lifestyle" name="lifestyle" max="10" required>
  <br><br>
  <input type="submit" value="Proceed"/>
</form>