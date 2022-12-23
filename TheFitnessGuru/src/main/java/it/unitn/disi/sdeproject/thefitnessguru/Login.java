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

        // Check if username and password parameters exists
        if (request.getParameter("username") != null && request.getParameter("password") != null) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            int user_id = MySQL_DB.Authenticate(username, password);

            if (user_id != -1) {
                //Creating new session
                session = Login.NewSession(request, user_id);

                response.sendRedirect("");

                return;
            } else
                errorMessage.setErrorMessage("Username or Password incorrect");
        } else if (request.getParameter("username") != null || request.getParameter("password") != null)
            errorMessage.setErrorMessage("Username and Password required");

        loadLoginPageJSP(request, response, errorMessage);
    }

    protected void loadLoginPageJSP(HttpServletRequest request, HttpServletResponse response, ErrorMessage errorMessage) throws ServletException, IOException
    {
        //Loading the login form
        String destination = "login.jsp";
        request.setAttribute("myErrorBean", errorMessage);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(destination);
        requestDispatcher.forward(request, response);
    }

    public static void loadLoginPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.sendRedirect("login");
    }

    public static HttpSession NewSession(HttpServletRequest request, int user_id) throws ServletException, IOException {
        //Creating new session
        HttpSession session = request.getSession(true);
        //Setting session timeout
        session.setMaxInactiveInterval(10 * 60);
        //Setting session user attributes
        session.setAttribute("user_id", user_id);
        session.setAttribute("ok", "ok");

        System.out.println("New session - User_id: " + user_id);

        return session;
    }

    public static void DestroySession(HttpServletRequest request) throws ServletException, IOException {
        //Getting session
        HttpSession session = request.getSession(false);

        int user_id = (int)session.getAttribute("user_id");

        //Invalidate the session
        session.invalidate();

        System.out.println("Destroy session - User_id: " + user_id);

        return;
    }
}

