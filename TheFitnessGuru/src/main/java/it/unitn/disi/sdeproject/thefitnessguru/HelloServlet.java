package it.unitn.disi.sdeproject.thefitnessguru;

import it.unitn.disi.sdeproject.db.MySQL_DB;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        ResultSet ris = MySQL_DB.execute("SELECT * FROM USERS");
        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + message + "</h1>");
        try
        {
            while (ris.next())
            {
                out.println("<h2>" + ris.getInt(1) + " " + ris.getString(3) + " " +  ris.getString(4)  + "</h2>");
            }
        }catch(SQLException e )
        {
            e.printStackTrace();
        }

        out.println("</body></html>");
    }

    public void destroy() {
    }
}