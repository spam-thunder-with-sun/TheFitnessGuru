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
import java.time.LocalDate;
import java.util.List;

import static it.unitn.disi.sdeproject.db.MySQL_DB_Get_Query.*;
import static it.unitn.disi.sdeproject.db.MySQL_DB_Set_Query.*;
import static it.unitn.disi.sdeproject.extrafeature.ExtraFeature.SendEmail;
import static it.unitn.disi.sdeproject.pdf.CreatePDF.CreatePDFDiet;

@WebServlet(name = "home_Nutritionist", value = "/home_Nutritionist")
public class Home_Nutritionist extends HttpServlet {
    /*
       Home Nutritionist servlet.
       This Servlet contains all the APIs Nutritionist-related.
    */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Nutritionist nutritionist = (Nutritionist) session.getAttribute("user");

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

        //getDietRequest
        if(request.getParameter("getDietRequest") != null && request.getParameter("getDietRequest").equalsIgnoreCase("true"))
        {
            //Get collaborations
            List<Collaboration> athleteCollaborations = GetNutritionistAthleteCollaboration(nutritionist.getUser_id());

            //Json parsing
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

            final StringBuilder allData = new StringBuilder();
            athleteCollaborations.forEach((collaboration) -> {
                List<Diet> diets = GetNutritionistAthleteDietRequests(collaboration.getCollaboration_id());

                diets.forEach((diet) -> {
                    String tmp = gson.toJson(diet).replace('}', ' ').trim();
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

        loadHomePageJSP(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Nutritionist nutritionist = (Nutritionist) session.getAttribute("user");

        //acceptCollaboration
        if(request.getParameter("acceptCollaboration") != null)
        {
            int collaboration_id = Integer.parseInt(request.getParameter("acceptCollaboration"));
            AcceptNutritionistAthleteCollaboration(nutritionist.getUser_id(), collaboration_id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        }

        //terminateCollaboration
        if(request.getParameter("terminateCollaboration") != null)
        {
            int collaboration_id = Integer.parseInt(request.getParameter("terminateCollaboration"));
            TerminateNutritionistAthleteCollaboration(nutritionist.getUser_id(), collaboration_id);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        }

        //createDiet
        if(request.getParameter("createDiet") != null && request.getParameter("data") != null)
        {
            int diet_id = Integer.parseInt(request.getParameter("createDiet"));
            String jsonData = request.getParameter("data").trim();
            //System.out.println("Create Diet " + diet_id + " : ");
            //System.out.println(jsonData);

            if(UpdateDietRequest(diet_id, jsonData))
            {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                String athleteEmail = GetNutritionistAthleteEmail(diet_id);

                String emailText = "Your nutritionist " + nutritionist.getFullName() + " has sent you a diet schedule. " +
                        "You can download it from your profile in the \"My nutritionist collaboration\" section.\n\n" +
                        "This email is automatically generated, please do not reply.";

                SendEmail(athleteEmail, "Diet Request Update", emailText);
            }
            else
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

            return;
        }
    }

    protected void loadHomePageJSP(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        //Loading the homepage form
        String destination = "home_Nutritionist.jsp";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(destination);
        requestDispatcher.forward(request, response);
    }
}
