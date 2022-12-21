package it.unitn.disi.sdeproject.thefitnessguru;

import it.unitn.disi.sdeproject.beans.ErrorMessage;
import it.unitn.disi.sdeproject.db.MySQL_DB;

import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "login", value = "/login")
public class Login extends HttpServlet {

    public void init() {}
    public void destroy() {}

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
        ErrorMessage errorMessage = new ErrorMessage();
        boolean success = false;

        //Check if a session already exist
        if (session == null || session.getAttribute("ok") == null || !session.getAttribute("ok").equals("ok")) {
            // Check if username and password parameters exists
            if (request.getParameter("username") != null && request.getParameter("password") != null) {
                String username = request.getParameter("username");
                String password = request.getParameter("password");

                int user_id = MySQL_DB.Authenticate(username, password);

                if (user_id != -1) {
                    //Creating new session
                    session = request.getSession(true);
                    //Setting session timeout
                    session.setMaxInactiveInterval(10 * 60);
                    //Setting session user attributes
                    session.setAttribute("user_id", user_id);
                    session.setAttribute("ok", "ok");
                    success = true;
                } else
                    errorMessage.setErrorMessage("Username or Password incorrect");
            } else if (request.getParameter("username") != null || request.getParameter("password") != null)
                    errorMessage.setErrorMessage("Username and Password required");
        }

        //System.out.println("Login - Successo: " + success + " Errore: " + errorMessage);

        //Forwarding the request
        if(success)
            //Loading the home page
            loadHomePage(request, response);
        else
            loadLoginForm(request, response, errorMessage);
    }

    void loadLoginForm(HttpServletRequest request, HttpServletResponse response, ErrorMessage errorMessage) throws ServletException, IOException
    {
        //Loading the login form
        String destination = "login.jsp";
        request.setAttribute("myErrorBean", errorMessage);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(destination);
        requestDispatcher.forward(request, response);
    }

    void loadHomePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //Loading the home page
        response.sendRedirect("");
    }

}

