package it.unitn.disi.sdeproject.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;

@WebServlet(name = "DietAdapter", value = "/home/DietAdapter")
public class DietAdapter extends HttpServlet
{
    private String x_api_key = "zJRM87GPTK87aIS0eC41lQ==yT387tRyKsu98tkd";
    private String baseURL = "https://api.api-ninjas.com/v1/recipe?";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doAll(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doAll(request, response);
    }

    private void doAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String query = "";
        Enumeration<String> paramList = request.getParameterNames();

        String complete_url = baseURL;
        while(paramList.hasMoreElements()){
            String TMP = paramList.nextElement();
            if(TMP.equals("query")){
                query = request.getParameter(TMP);
                complete_url += ("query="+query);
            }
        }

        URL url = new URL(complete_url);
        System.out.println("DIET ADAPTER --> GET REQUEST: " + complete_url);
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestProperty("Accept", "application/json");
        http.setRequestProperty("x-api-key", x_api_key);
        http.setReadTimeout(5000);
        System.out.println("RESPONSE CODE:" + http.getResponseCode());

        if (http.getResponseCode() == http.HTTP_OK){
            //Json parsing
            BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line+"\n");
            }
            br.close();
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
            String tosend = gson.toJson(sb.toString());
            //SEND RESPONSE
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(tosend);
        }else{
            //SEND RESPONSE
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print("SC_INTERNAL_SERVER_ERROR");
        }
        http.disconnect();
    }
}
