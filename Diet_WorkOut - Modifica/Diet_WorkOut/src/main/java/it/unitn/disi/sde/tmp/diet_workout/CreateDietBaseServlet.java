package it.unitn.disi.sde.tmp.diet_workout;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CreateDietBaseServlet", value = "/create-diet-base")
public class CreateDietBaseServlet extends HttpServlet
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
        System.out.println("CREATE DIET BASE SERVLET");

        // Set the ContentType
        response.setContentType("text/html;charset=UTF-8");

        // Include header
        request.getRequestDispatcher("Auth/header.jsp").include(request, response);

        // Create page body
        request.getRequestDispatcher("Auth/base_diet.jsp").include(request, response);

        // Include footer
        request.getRequestDispatcher("Auth/footer.jsp").include(request, response);
    }
}
