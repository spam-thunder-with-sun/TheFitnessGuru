package it.unitn.disi.sdeproject.extrafeature;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;

import static it.unitn.disi.sdeproject.email.SendEmail.SendEmail;
import static it.unitn.disi.sdeproject.pdf.CreatePDF.CreatePDFDiet;
import static it.unitn.disi.sdeproject.pdf.CreatePDF.CreatePDFWorkout;

@WebServlet(name = "createPDF", value = "/createPDF")
public class CreatePDF extends HttpServlet {
    public void init() {}

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doAll(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doAll(request, response);
    }

    protected void doAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        System.out.println("Richiesta PDF");

        //PDF Diet
        if(request.getParameter("pdfdiet") != null && request.getParameter("id") != null && request.getParameter("jsonData") != null)
        {
            int diet_id = Integer.parseInt(request.getParameter("id"));
            String jsonData = request.getParameter("jsonData").trim();

            System.out.println("diet_id: " + diet_id);
            System.out.println("jsonData: " + jsonData);
            System.out.println();

            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=" + "Diet" + diet_id + "_" + LocalDate.now() +  ".pdf");
            response.setCharacterEncoding("UTF-8");

            String pathImg = getServletContext().getRealPath("img/UniOfTrento.png");

            //Pass the out stream
            CreatePDFDiet(jsonData, pathImg, response.getOutputStream());

            return;
        }

        //PDF Workout
        if(request.getParameter("pdfworkout") != null && request.getParameter("id") != null && request.getParameter("jsonData") != null)
        {
            int workout_id = Integer.parseInt(request.getParameter("id"));
            String jsonData = request.getParameter("jsonData").trim();

            System.out.println("workout_id: " + workout_id);
            System.out.println("jsonData: " + jsonData);
            System.out.println();

            response.setContentType("application/pdf");
            response.setHeader("Content-disposition", "attachment; filename=" + "Workout" + workout_id + "_" + LocalDate.now() +  ".pdf");
            response.setCharacterEncoding("UTF-8");
            response.setStatus(HttpServletResponse.SC_OK);

            String pathImg = getServletContext().getRealPath("img/UniOfTrento.png");

            //Pass the out stream
            CreatePDFWorkout(jsonData, pathImg, response.getOutputStream());

            return;
        }
    }
}