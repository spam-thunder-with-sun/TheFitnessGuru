package it.unitn.disi.sdeproject.thefitnessguru;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unitn.disi.sdeproject.beans.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

import static it.unitn.disi.sdeproject.db.MySQL_DB_Get_Query.*;
import static it.unitn.disi.sdeproject.db.MySQL_DB_Set_Query.*;
import static it.unitn.disi.sdeproject.pdf.CreatePDF.CreatePDFDiet;
import static it.unitn.disi.sdeproject.pdf.CreatePDF.CreatePDFWorkout;

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

        //getTrainerCollaborations
        if(request.getParameter("getTrainerCollaborations") != null && request.getParameter("getTrainerCollaborations").equalsIgnoreCase("true"))
        {
            //Get collaborations
            List<Collaboration> trainerCollaboration = GetTrainerCollaboration(athlete.getUser_id());
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

        //getNewPossibleTrainer
        if(request.getParameter("getNewPossibleTrainer") != null && request.getParameter("getNewPossibleTrainer").equalsIgnoreCase("true"))
        {
            //Get possible trainers
            List<Professional> possibleTrainers = GetNewPossibleTrainers(athlete.getUser_id());
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
            CreateTrainerCollaboration(athlete.getUser_id(), trainer_id, 1);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);

            return;
        }

        //getWorkoutRequest
        if(request.getParameter("getWorkoutRequest") != null)
        {
            int collab_id = Integer.parseInt(request.getParameter("getWorkoutRequest"));
            //Get workouts
            List<Workout> workoutRequest = GetWorkoutRequest(collab_id);
            //Json parsing
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("dd MMM yyyy").setPrettyPrinting().create();
            String tosend = gson.toJson(workoutRequest);
            //Send
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(tosend);

            return;
        }

        //getWorkoutResponse
        if(request.getParameter("getWorkoutResponse") != null)
        {
            int workout_id = Integer.parseInt(request.getParameter("getWorkoutResponse"));
            String json = GetWorkoutResponse(workout_id);

            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=" + "Workout" + workout_id + "_" + LocalDate.now() +  ".pdf");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);

            String pathImg = getServletContext().getRealPath("img/UniOfTrento.png");

            //Pass the out stream
            CreatePDFWorkout(json, pathImg, response.getOutputStream());

            return;
        }

        //createWorkoutRequest
        if(request.getParameter("createWorkoutRequest") != null && request.getParameter("workout_goal") != null  &&
                request.getParameter("workout_days") != null  && request.getParameter("health_notes") != null)
        {
            int collaboration_id = Integer.parseInt(request.getParameter("createWorkoutRequest"));
            String workout_goal =  request.getParameter("workout_goal");
            String health_notes =  request.getParameter("health_notes");
            int workout_days = Integer.parseInt(request.getParameter("workout_days"));

            CreateWorkoutRequest(collaboration_id, workout_goal, workout_days, health_notes);

            response.setStatus(HttpServletResponse.SC_NO_CONTENT);

            return;
        }

        //--------------------------------Diet------------------------------------


        //getNutritionistCollaborations
        if(request.getParameter("getNutritionistCollaborations") != null && request.getParameter("getNutritionistCollaborations").equalsIgnoreCase("true"))
        {
            //Get collaborations
            List<Collaboration> nutritionistCollaboration = GetNutritionistCollaboration(athlete.getUser_id());
            //Json parsing
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
            String tosend = gson.toJson(nutritionistCollaboration);
            //Send
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(tosend);

            return;
        }

        //getNewPossibleNutritionist
        if(request.getParameter("getNewPossibleNutritionist") != null && request.getParameter("getNewPossibleNutritionist").equalsIgnoreCase("true"))
        {
            //Get possible nutritionists
            List<Professional> possibleNutritionists = GetNewPossibleNutritionists(athlete.getUser_id());
            //Json parsing
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
            String tosend = gson.toJson(possibleNutritionists);
            //Send
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(tosend);

            return;
        }

        //createNutritionistCollaboration
        if(request.getParameter("createNutritionistCollaboration") != null)
        {
            int nutritionist_id = Integer.parseInt(request.getParameter("createNutritionistCollaboration"));
            CreateNutritionistCollaboration(athlete.getUser_id(), nutritionist_id, 1);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);

            return;
        }

        //getDietRequest
        if(request.getParameter("getDietRequest") != null)
        {
            int collab_id = Integer.parseInt(request.getParameter("getDietRequest"));
            //Get diets
            List<Diet> dietRequest = GetDietRequest(collab_id);
            //Json parsing
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("dd MMM yyyy").setPrettyPrinting().create();
            String tosend = gson.toJson(dietRequest);
            //Send
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(tosend);

            return;
        }

        //getDietResponse
        if(request.getParameter("getDietResponse") != null)
        {
            int diet_id = Integer.parseInt(request.getParameter("getDietResponse"));
            String json = GetDietResponse(diet_id);

            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=" + "Diet" + diet_id + "_" + LocalDate.now() +  ".pdf");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);

            String pathImg = getServletContext().getRealPath("img/UniOfTrento.png");

            //Pass the out stream
            CreatePDFDiet(json, pathImg, response.getOutputStream());

            return;
        }

        //createDietRequest
        if(request.getParameter("createDietRequest") != null && request.getParameter("diet_goal") != null  &&
                request.getParameter("allergies") != null  && request.getParameter("intolerances") != null &&
                request.getParameter("basal_metabolic_rate") != null && request.getParameter("lifestyle") != null)
        {
            int collaboration_id = Integer.parseInt(request.getParameter("createDietRequest"));
            String allergies =  request.getParameter("allergies");
            String intolerances =  request.getParameter("intolerances");
            Integer basal_metabolic_rate = Integer.valueOf(request.getParameter("basal_metabolic_rate"));
            String diet_goal =  request.getParameter("diet_goal");
            Integer lifestyle = Integer.valueOf(request.getParameter("lifestyle"));

            CreateDietRequest(collaboration_id, allergies, intolerances, basal_metabolic_rate, diet_goal, lifestyle);

            response.setStatus(HttpServletResponse.SC_NO_CONTENT);

            return;
        }

        //-------------------------------------------------------

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
