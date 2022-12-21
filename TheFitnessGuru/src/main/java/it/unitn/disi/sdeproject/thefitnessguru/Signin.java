package it.unitn.disi.sdeproject.thefitnessguru;

import it.unitn.disi.sdeproject.beans.ErrorMessage;

import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "signin", value = "/signin")
public class Signin extends HttpServlet {
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
        loadSigninForm(request, response, new ErrorMessage());
    }

    void loadSigninForm(HttpServletRequest request, HttpServletResponse response, ErrorMessage errorMessage) throws ServletException, IOException
    {
        //Loading the signin form
        String destination = "signin.jsp";
        request.setAttribute("myErrorBean", errorMessage);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(destination);
        requestDispatcher.forward(request, response);
    }

    void loadHomePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //Loading the home page
        response.sendRedirect("");
    }
}
