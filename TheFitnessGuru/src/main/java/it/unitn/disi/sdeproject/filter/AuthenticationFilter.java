package it.unitn.disi.sdeproject.filter;

import it.unitn.disi.sdeproject.thefitnessguru.Home;
import it.unitn.disi.sdeproject.thefitnessguru.Login;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AuthenticationFilter")
public class AuthenticationFilter implements Filter {
    public void init(FilterConfig config) {

    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession(false);
        String uri = request.getRequestURI().toLowerCase();
        boolean validSession = session != null && session.getAttribute("ok") != null && session.getAttribute("ok").equals("ok");
        System.out.println("Req: " + uri + " Session: " + validSession);

        //is a css file or a js file or ... is not requested
        if( uri.endsWith("css") || uri.endsWith("js") || uri.endsWith("png") || uri.endsWith("jpg"))
        {
            System.out.println("Loading special file");
            // pass the request along the filter chain
            chain.doFilter(request, response);

            return;
        }

        //If there is no valid session and the login page or the signin page or ... are not requested, I'll load the login page
        if(!validSession && !uri.endsWith("login") && !uri.endsWith("signin") && !uri.endsWith("index.jsp") && !uri.endsWith("/"))
        {
            System.out.println("Loading login page");
            Login.loadLoginPage(response);

            return;
        }

        //If there is a valid session and the login page or the signin page is requested, I'll load the home page
        if(validSession &&  ( uri.endsWith("login") || uri.endsWith("signin") ))
        {
            System.out.println("Loading home page");
            Home.loadHomePage(request, response);

            return;
        }

        //If there is a valid session and the home_? page is requested, I'll load the home page
        //This is to prevent for example an Athlete to access the home_Nutritionist page
        if(validSession &&  ( uri.endsWith("home_athlete" ) || uri.endsWith("home_nutritionist") || uri.endsWith("home_trainer")))
        {
            System.out.println("Loading home page");
            Home.loadHomePage(request, response);

            return;
        }

        //Loading whatever is requested
        System.out.println("Loading whatever is requested");
        // pass the request along the filter chain
        chain.doFilter(request, response);
    }
}