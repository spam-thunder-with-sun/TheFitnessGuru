package it.unitn.disi.sdeproject.thefitnessguru;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "home_Athlete", value = "/home_Athlete")
public class Home_Athlete extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doAll(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doAll(request, response);
    }

    protected void doAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //HttpSession session = request.getSession(false);

        //Logout
        if(request.getParameter("logout") != null && request.getParameter("logout").equalsIgnoreCase("ok"))
        {
            Login.DestroySession(request);

            response.sendRedirect("");

            return;
        }

        loadHomePageJSP(request, response);
    }

    protected void loadHomePageJSP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //Loading the homepage form
        String destination = "home_Athlete.jsp";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(destination);
        requestDispatcher.forward(request, response);
    }
}
