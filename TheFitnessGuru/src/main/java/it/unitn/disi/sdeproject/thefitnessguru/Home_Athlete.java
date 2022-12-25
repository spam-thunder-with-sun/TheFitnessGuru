package it.unitn.disi.sdeproject.thefitnessguru;

import it.unitn.disi.sdeproject.beans.Athlete;
import it.unitn.disi.sdeproject.beans.Collaboration;
import it.unitn.disi.sdeproject.beans.Professional;
import it.unitn.disi.sdeproject.beans.Workout;

import static it.unitn.disi.sdeproject.db.MySQL_DB_Set_Query.*;
import static it.unitn.disi.sdeproject.db.MySQL_DB_Get_Query.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
        HttpSession session = request.getSession(false);
        Athlete athlete = (Athlete) session.getAttribute("user");

        //Logout
        if(request.getParameter("logout") != null && request.getParameter("logout").equalsIgnoreCase("ok"))
        {
            Login.DestroySession(request);

            response.sendRedirect("");

            return;
        }

        //getTrainerCollaborations
        if(request.getParameter("getTrainerCollaborations") != null && request.getParameter("getTrainerCollaborations").equalsIgnoreCase("true"))
        {
            //Get collaborations
            List<Collaboration> trainerCollaboration = getTrainerCollaboration(athlete);
            //Json parsing
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
            String tosend = gson.toJson(trainerCollaboration);
            //Send
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(tosend);

            return;
        }

        //getWorkoutRequest
        if(request.getParameter("getWorkoutRequest") != null)
        {
            int collab_id = Integer.parseInt(request.getParameter("getWorkoutRequest"));
            //Get workouts
            List<Workout> workoutRequest = getWorkoutRequest(collab_id);
            //Json parsing
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
            String tosend = gson.toJson(workoutRequest);
            //Send
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(tosend);

            return;
        }

        //getNewPossibleTrainer
        if(request.getParameter("getNewPossibleTrainer") != null && request.getParameter("getNewPossibleTrainer").equalsIgnoreCase("true"))
        {
            //Get possible trainers
            List<Professional> possibleTrainers = getNewPossibleTrainers(athlete.getUser_id());
            //Json parsing
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
            String tosend = gson.toJson(possibleTrainers);
            //Send
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(tosend);

            return;
        }

        //createTrainerCollaboration
        if(request.getParameter("createTrainerCollaboration") != null)
        {
            int trainer_id = Integer.parseInt(request.getParameter("createTrainerCollaboration"));
            CreateTrainerCollaboration(athlete.getUser_id(), trainer_id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);

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
