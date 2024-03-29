package it.unitn.disi.sdeproject.thefitnessguru;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unitn.disi.sdeproject.beans.Collaboration;
import it.unitn.disi.sdeproject.beans.Trainer;
import it.unitn.disi.sdeproject.beans.Workout;

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
import static it.unitn.disi.sdeproject.extrafeature.ExtraFeature.SendEmail;
import static it.unitn.disi.sdeproject.pdf.CreatePDF.CreatePDFWorkout;

@WebServlet(name = "home_Trainer", value = "/home_Trainer")
public class Home_Trainer extends HttpServlet {
    /*
        Home Trainer servlet.
        This Servlet contains all the APIs Trainer-related.
    */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Trainer trainer = (Trainer) session.getAttribute("user");

        //getAthleteCollaborations
        if(request.getParameter("getAthleteCollaborations") != null && request.getParameter("getAthleteCollaborations").equalsIgnoreCase("true"))
        {
            //Get collaborations
            List<Collaboration> athleteCollaborations = GetTrainerAthleteCollaboration(trainer.getUser_id());
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

        //getWorkoutRequest
        if(request.getParameter("getWorkoutRequest") != null && request.getParameter("getWorkoutRequest").equalsIgnoreCase("true"))
        {
            //Get collaborations
            List<Collaboration> athleteCollaborations = GetTrainerAthleteCollaboration(trainer.getUser_id());

            //Json parsing
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

            final StringBuilder allData = new StringBuilder();
            athleteCollaborations.forEach((collaboration) -> {
                List<Workout> workouts = GetTrainerAthleteWorkoutRequests(collaboration.getCollaboration_id());

                workouts.forEach((workout) -> {
                    String tmp = gson.toJson(workout).replace('}', ' ').trim();
                    tmp += ",\n  \"athlete_full_name\": \"" + collaboration.getFullName() + "\"\n},";
                    allData.append(tmp);
                });
            });

            String tosend = allData.toString();

            //If not void remove last char
            if(tosend.length() > 0)
                tosend = tosend.substring(0, tosend.length()-1);

            tosend = "[" + tosend + "]";

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

        loadHomePageJSP(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Trainer trainer = (Trainer) session.getAttribute("user");

        //acceptCollaboration
        if(request.getParameter("acceptCollaboration") != null)
        {
            int collaboration_id = Integer.parseInt(request.getParameter("acceptCollaboration"));
            AcceptTrainerAthleteCollaboration(trainer.getUser_id(), collaboration_id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        }

        //terminateCollaboration
        if(request.getParameter("terminateCollaboration") != null)
        {
            int collaboration_id = Integer.parseInt(request.getParameter("terminateCollaboration"));
            TerminateTrainerAthleteCollaboration(trainer.getUser_id(), collaboration_id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        }

        //createWorkout
        if(request.getParameter("createWorkout") != null && request.getParameter("data") != null)
        {
            int workout_id = Integer.parseInt(request.getParameter("createWorkout"));
            String jsonData = request.getParameter("data").trim();
            System.out.println("Create Workout " + workout_id + " : ");
            System.out.println(jsonData);

            if(UpdateWorkoutRequest(workout_id, jsonData))
            {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);

                String athleteEmail = GetTrainerAthleteEmail(workout_id);

                String emailText = "Your trainer " + trainer.getFullName() + " has sent you a workout schedule. " +
                        "You can download it from your profile in the \"My trainer collaboration\" section.\n\n" +
                        "This email is automatically generated, please do not reply.";

                SendEmail(athleteEmail, "Workout Request Update", emailText);
            }
            else
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            return;
        }
    }

    protected void loadHomePageJSP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //Loading the homepage form
        String destination = "home_Trainer.jsp";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(destination);
        requestDispatcher.forward(request, response);
    }
}
