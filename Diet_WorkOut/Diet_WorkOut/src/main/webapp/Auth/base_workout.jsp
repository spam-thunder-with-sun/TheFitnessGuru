<form action="create-workout-days">
    <label for="name">Workout name:</label>
    <br>
    <input type="text" id="name" name="name" required>
    <br><br>
    <label for="days">Workout days:</label>
    <br>
    <input type="number" id="days" name="days" min="1" max="7" required>
    <br><br>
    <label for="goals">Workout goals:</label>
    <br>
    <input type="text" id="goals" name="goals" maxlength="100" required>
    <br><br>
    <input type="submit" value="Proceed"/>
</form>