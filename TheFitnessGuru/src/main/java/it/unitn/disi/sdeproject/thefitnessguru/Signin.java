package it.unitn.disi.sdeproject.thefitnessguru;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unitn.disi.sdeproject.beans.ErrorMessage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static it.unitn.disi.sdeproject.db.MySQL_DB_Set_Query.*;


@SuppressWarnings("CommentedOutCode")
@WebServlet(name = "signin", value = "/signin")
public class Signin extends HttpServlet {
    /*
        Signin servlet.
        This Servlet contains all the APIs signin-related.
    */
    public void init() {}
    public void destroy() {}
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        loadSigninPageJSP(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ErrorMessage errorMessage = new ErrorMessage();

        //Check if al least one parameter is set
        if(isSignin(request))
        {
            // Check if all parameters exists
            if (isValid(request))
            {
                String name = request.getParameter("name").trim();
                String surname = request.getParameter("surname").trim();
                String birthday = request.getParameter("birthday");
                String gender = request.getParameter("gender").trim();
                String email = request.getParameter("email").trim();
                String username = request.getParameter("username").trim();
                String password = request.getParameter("password").trim();
                String account_type = request.getParameter("account_type").trim().toLowerCase();

                if(gender.equalsIgnoreCase("female"))
                    gender = "F";
                else
                    gender = "M";

                int user_id = -1;

                if(account_type.equalsIgnoreCase("athlete"))
                {
                    String sport = request.getParameter("sport").trim();
                    String height = request.getParameter("height").trim();
                    String weight = request.getParameter("weight").trim();

                    user_id = CreateAthlete(name, surname, birthday, gender, email, username, password, sport, height, weight);
                }
                else if(account_type.equalsIgnoreCase("trainer"))
                {
                    String title = request.getParameter("title").trim();
                    String description = request.getParameter("description").trim();

                    user_id = CreateTrainer(name, surname, birthday, gender, email, username, password, title, description);
                }
                else if(account_type.equalsIgnoreCase("nutritionist"))
                {
                    String title = request.getParameter("title").trim();
                    String description = request.getParameter("description").trim();

                    user_id = CreateNutritionist(name, surname, birthday, gender, email, username, password, title, description);
                }

                if(user_id != -1) {
                    //Creating new session
                    Login.NewSession(request, user_id);

                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);

                    return;
                }
                else
                {
                    errorMessage.setErrorMessage("Something went wrong");
                    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
                    String tosend = gson.toJson(errorMessage);

                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter out = response.getWriter();
                    out.print(tosend);

                    return;
                }
            }
        }
    }


    void loadSigninPageJSP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //Loading the signin form
        String destination = "signin.jsp";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(destination);
        requestDispatcher.forward(request, response);
    }

    private boolean isValid(HttpServletRequest request)
    {
        boolean valid = request.getParameter("name") != null;
        valid &= request.getParameter("surname") != null;
        valid &= request.getParameter("birthday") != null;
        valid &= request.getParameter("gender") != null;
        valid &= request.getParameter("username") != null;
        valid &= request.getParameter("password") != null;
        valid &= request.getParameter("account_type") != null;

        if(valid)
        {
            String account_type = request.getParameter("account_type").trim().toLowerCase();
            if(account_type.equalsIgnoreCase("athlete"))
            {
                valid = request.getParameter("sport") != null;
                valid &= request.getParameter("height") != null;
                valid &= request.getParameter("weight") != null;
            }
            else if(account_type.equalsIgnoreCase("trainer") || account_type.equalsIgnoreCase("nutritionist"))
            {
                valid = request.getParameter("title") != null;
                valid &= request.getParameter("description") != null;
            }else
                valid = false;
        }

        return valid;
    }

    private boolean isSignin(HttpServletRequest request)
    {
        //Something is false when no parameter is found, true otherwise
        boolean something = request.getParameter("name") != null;
        something |= request.getParameter("surname") != null;
        something |= request.getParameter("birthday") != null;
        something |= request.getParameter("gender") != null;
        something |= request.getParameter("username") != null;
        something |= request.getParameter("password") != null;
        something |= request.getParameter("account_type") != null;
        something |= request.getParameter("sport") != null;
        something |= request.getParameter("height") != null;
        something |= request.getParameter("weight") != null;
        something |= request.getParameter("title") != null;
        something |= request.getParameter("description") != null;

        return something;
    }
}
