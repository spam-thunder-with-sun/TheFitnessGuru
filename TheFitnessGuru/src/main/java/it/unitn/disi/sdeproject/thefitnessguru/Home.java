package it.unitn.disi.sdeproject.thefitnessguru;

import it.unitn.disi.sdeproject.beans.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@SuppressWarnings("unused")
@WebServlet(name = "home", value = "/home")
public class Home extends HttpServlet {
    /*
        Home servlet. This servlet redirect the request to the proper servlet depending from the destination (user_type)
    */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doAll(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doAll(request, response);
    }

    protected void doAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        //Logout
        if(request.getParameter("logout") != null && request.getParameter("logout").equalsIgnoreCase("ok"))
        {
            Login.DestroySession(request);

            response.sendRedirect("login");

            return;
        }

        //Get user info
        User myUser = (User)session.getAttribute("user");

        if(myUser instanceof Athlete)
        {
            String destination = "home_Athlete";
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(destination);
            requestDispatcher.forward(request, response);
        }
        else if(myUser instanceof Nutritionist)
        {
            String destination = "home_Nutritionist";
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(destination);
            requestDispatcher.forward(request, response);
        }
        else if(myUser instanceof Trainer)
        {
            String destination = "home_Trainer";
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(destination);
            requestDispatcher.forward(request, response);
        }
    }

    public static void loadHomePage(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        response.sendRedirect("home");
    }

}
