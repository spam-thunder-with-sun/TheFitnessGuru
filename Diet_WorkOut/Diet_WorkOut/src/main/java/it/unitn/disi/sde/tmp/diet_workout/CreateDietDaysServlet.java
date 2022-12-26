package it.unitn.disi.sde.tmp.diet_workout;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CreateDietDaysServlet", value = "/create-diet-days")
public class CreateDietDaysServlet extends HttpServlet
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
        System.out.println("CREATE DIET DAYS SERVLET");

        String name, allergies, intolerances, goals;
        Integer bmr, lifestyle;

        // Get parameters from FORM
        name  = request.getParameter("name");
        allergies  = request.getParameter("allergies");
        intolerances  = request.getParameter("intolerances");
        goals  = request.getParameter("goals");
        bmr  = Integer.parseInt(request.getParameter("bmr"));
        lifestyle  = Integer.parseInt(request.getParameter("lifestyle"));
        request.setAttribute("name", name);
        request.setAttribute("allergies", allergies);
        request.setAttribute("intolerances", intolerances);
        request.setAttribute("goals", goals);
        request.setAttribute("bmr", bmr);
        request.setAttribute("lifestyle", lifestyle);

        // Set the ContentType
        response.setContentType("text/html;charset=UTF-8");

        // Include header
        request.getRequestDispatcher("Auth/header.jsp").include(request, response);

        // Create page body
        request.getRequestDispatcher("Auth/day_diet.jsp").include(request, response);

        //Include footer
        request.getRequestDispatcher("Auth/footer.jsp").include(request, response);
    }
}
