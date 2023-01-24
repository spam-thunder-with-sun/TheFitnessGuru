package it.unitn.disi.sde.tmp.diet_workout;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import org.json.*;

@WebServlet(name = "JSONReceiverServlet", value = "/JSONReceiverServlet")
public class JSONReceiverServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doRequest(request, response);
    }

    private void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        System.out.println("JSON RECEIVER SERVLET");

        String jsonSTR = request.getParameter("json");
        JSONObject jsonObj = new JSONObject(jsonSTR);
        System.out.println(jsonObj);
    }
}
