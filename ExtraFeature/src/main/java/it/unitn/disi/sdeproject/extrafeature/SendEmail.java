package it.unitn.disi.sdeproject.extrafeature;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import static it.unitn.disi.sdeproject.email.SendEmail.SendEmail;

@WebServlet(name = "sendEmail", value = "/sendEmail")
public class SendEmail extends HttpServlet {
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

        System.out.println("Richiesta Email");

        //sendEmail
        if(request.getParameter("athleteEmail") != null &&
                request.getParameter("emailSubject") != null && request.getParameter("emailText") != null)
        {
            String athleteEmail = request.getParameter("athleteEmail").trim();
            String emailSubject = request.getParameter("emailSubject").trim();
            String emailText = request.getParameter("emailText").trim();

            System.out.println("Email: " + athleteEmail);
            System.out.println("Subject: " + emailSubject);
            System.out.println("Text: " + emailText);
            System.out.println();

            SendEmail(athleteEmail, emailSubject, emailText);

            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        }
    }
}