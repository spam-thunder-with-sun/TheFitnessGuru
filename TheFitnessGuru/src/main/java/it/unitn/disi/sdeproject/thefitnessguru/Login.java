package it.unitn.disi.sdeproject.thefitnessguru;

import it.unitn.disi.sdeproject.beans.ErrorMessage;
import it.unitn.disi.sdeproject.beans.User;

import static it.unitn.disi.sdeproject.db.MySQL_DB_Get_Query.*;
import static it.unitn.disi.sdeproject.db.MySQL_DB.*;

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
        ErrorMessage errorMessage = new ErrorMessage();

        /*
        // Check if username and password parameters exists
        if (request.getParameter("username") != null && request.getParameter("password") != null)
        {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            */
        //To speed up things
        HttpSession session = request.getSession(false);
        boolean validSession = session != null && session.getAttribute("ok") != null && session.getAttribute("ok").equals("ok");
        if(!validSession)
        {
            String username = "stefanotrick";
            String password = "stefanotrick";

            int user_id = Authenticate(username, password);

            if (user_id != -1) {
                //Creating new session
                Login.NewSession(request, user_id);

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

    public static void loadLoginPage(HttpServletResponse response) throws IOException
    {
        response.sendRedirect("login");
    }

    public static void NewSession(HttpServletRequest request, int user_id) {
        //Creating new session
        HttpSession session = request.getSession(true);
        //Setting session timeout
        session.setMaxInactiveInterval(10 * 60);
        //Get user info
        User myUser = getUser(user_id);
        //Setting session user attributes
        session.setAttribute("user", myUser);
        session.setAttribute("ok", "ok");

        System.out.println("New session - User_id: " + myUser.getUser_id());
    }

    public static void DestroySession(HttpServletRequest request) {
        //Getting session
        HttpSession session = request.getSession(false);

        User myUser = (User)session.getAttribute("user");

        //Invalidate the session
        session.invalidate();

        System.out.println("Destroy session - User_id: " + myUser.getUser_id());

    }
}

