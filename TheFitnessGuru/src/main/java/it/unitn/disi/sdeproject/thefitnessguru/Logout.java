package it.unitn.disi.sdeproject.thefitnessguru;

import it.unitn.disi.sdeproject.beans.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "logout", value = "/logout")
public class Logout extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Logout
        if(request.getParameter("logout") != null && request.getParameter("logout").equalsIgnoreCase("ok"))
        {
            DestroySession(request);

            response.setStatus(HttpServletResponse.SC_NO_CONTENT);

            return;
        }
    }

    public static void DestroySession(HttpServletRequest request) {
        //Getting session
        HttpSession session = request.getSession(false);

        User myUser = (User)session.getAttribute("user");

        //Invalidate the session
        session.invalidate();

        System.out.println("Destroy session - User_id: " + myUser.getUser_id());
    }
}
