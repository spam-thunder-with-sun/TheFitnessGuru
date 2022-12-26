package it.unitn.disi.sde.tmp.diet_workout;

import it.unitn.disi.sde.tmp.diet_workout.exercises.Utils;
import it.unitn.disi.sde.tmp.diet_workout.exercises.Workout;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet
{
    private String message;

    public void init()
    {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        response.setContentType("text/html");

        Workout workout = new Workout();

        String json = workout.getExercises("", Utils.Types.cardio, null, null);




        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void destroy()
    {
    }
}