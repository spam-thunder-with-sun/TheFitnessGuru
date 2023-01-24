package it.unitn.disi.sdeproject.thefitnessguru;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unitn.disi.sdeproject.beans.Collaboration;
import it.unitn.disi.sdeproject.beans.Diet;
import it.unitn.disi.sdeproject.beans.Nutritionist;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static it.unitn.disi.sdeproject.db.MySQL_DB_Get_Query.GetNutritionistAthleteCollaboration;
import static it.unitn.disi.sdeproject.db.MySQL_DB_Get_Query.GetNutritionistAthleteDietRequests;
import static it.unitn.disi.sdeproject.db.MySQL_DB_Set_Query.AcceptNutritionistAthleteCollaboration;

@WebServlet(name = "home_Nutritionist", value = "/home_Nutritionist")
public class Home_Nutritionist extends HttpServlet {
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
        Nutritionist nutritionist = (Nutritionist) session.getAttribute("user");

        //Logout
        if(request.getParameter("logout") != null && request.getParameter("logout").equalsIgnoreCase("ok"))
        {
            Login.DestroySession(request);

            response.sendRedirect("");

            return;
        }

        //-----------------------------------------------------------

        //getAthleteCollaborations
        if(request.getParameter("getAthleteCollaborations") != null && request.getParameter("getAthleteCollaborations").equalsIgnoreCase("true"))
        {
            //Get collaborations
            List<Collaboration> athleteCollaborations = GetNutritionistAthleteCollaboration(nutritionist.getUser_id());
            //Json parsing
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
            String tosend = gson.toJson(athleteCollaborations);
            //Send
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(tosend);

            return;
        }

        //acceptCollaboration
        if(request.getParameter("acceptCollaboration") != null)
        {
            int collaboration_id = Integer.parseInt(request.getParameter("acceptCollaboration"));
            AcceptNutritionistAthleteCollaboration(nutritionist.getUser_id(), collaboration_id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        }

        //getDietRequest
        if(request.getParameter("getDietRequest") != null && request.getParameter("getDietRequest").equalsIgnoreCase("true"))
        {
            //Get collaborations
            List<Collaboration> athleteCollaborations = GetNutritionistAthleteCollaboration(nutritionist.getUser_id());
            List<Diet> diets = new ArrayList<>();

            athleteCollaborations.forEach((collaboration) -> {
                diets.addAll(GetNutritionistAthleteDietRequests(collaboration.getCollaboration_id()));
            });

            //Json parsing
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
            String tosend = gson.toJson(diets);
            //Send
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(tosend);

            return;
        }

        loadHomePageJSP(request, response);
    }

    protected void loadHomePageJSP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //Loading the homepage form
        String destination = "home_Nutritionist.jsp";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(destination);
        requestDispatcher.forward(request, response);
    }
}
