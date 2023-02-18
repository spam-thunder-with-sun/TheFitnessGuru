package it.unitn.disi.sdeproject.thefitnessguru;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unitn.disi.sdeproject.beans.ErrorMessage;
import it.unitn.disi.sdeproject.beans.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

import static it.unitn.disi.sdeproject.db.MySQL_DB.Authenticate;
import static it.unitn.disi.sdeproject.db.MySQL_DB_Get_Query.GetUserType;

@WebServlet(name = "login", value = "/login")
public class Login extends HttpServlet {
    /*
        Login servlet.
        This Servlet contains all the APIs login-related.
        Sets also all the session parameters needed.
    */

    public void init() {}
    public void destroy() {}

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        loadLoginPageJSP(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ErrorMessage errorMessage = new ErrorMessage();

        // Check if username and password parameters exists
        if (request.getParameter("username") != null && request.getParameter("password") != null)
        {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            int user_id = Authenticate(username, password);

            if (user_id != -1) {
                //Creating new session
                Login.NewSession(request, user_id);

                response.setStatus(HttpServletResponse.SC_NO_CONTENT);

                return;
            } else
                errorMessage.setErrorMessage("Username or Password incorrect");
        } else if (request.getParameter("username") != null || request.getParameter("password") != null)
            errorMessage.setErrorMessage("Username and Password required");

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
        String tosend = gson.toJson(errorMessage);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(tosend);
    }


    protected void loadLoginPageJSP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //Loading the login form
        String destination = "login.jsp";
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
        User myUser = GetUserType(user_id);
        //Setting session user attributes
        session.setAttribute("user", myUser);
        session.setAttribute("ok", "ok");

        System.out.println("New session - User_id: " + myUser.getUser_id());
    }
}

