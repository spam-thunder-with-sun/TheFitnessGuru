package it.unitn.disi.sde.tmp.diet_workout;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CreateWorkoutDaysServlet", value = "/create-workout-days")
public class CreateWorkoutDaysServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doRequest(request, response);
    }

    private void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("CREATE WORKOUT DAYS SERVLET");

        String name, goals;
        Integer days;

        // Get parameters from FORM
        name  = request.getParameter("name");
        days  = Integer.parseInt(request.getParameter("days"));
        goals = request.getParameter("goals");
        request.setAttribute("name", name);
        request.setAttribute("days", days);
        request.setAttribute("goals", goals);

        // Set the ContentType
        response.setContentType("text/html;charset=UTF-8");

        // Include header
        request.getRequestDispatcher("Auth/header.jsp").include(request, response);

        // Create page body
        request.getRequestDispatcher("Auth/day_workout.jsp").include(request, response);

        //Include footer
        request.getRequestDispatcher("Auth/footer.jsp").include(request, response);
    }
}
