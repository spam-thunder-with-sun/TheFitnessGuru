package it.unitn.disi.sdeproject.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;

@WebServlet(name = "WorkoutAdapter", value = "/home/WorkoutAdapter")
public class WorkoutAdapter extends HttpServlet
{
    private String x_api_key = "zJRM87GPTK87aIS0eC41lQ==yT387tRyKsu98tkd";
    private String baseURL = "https://api.api-ninjas.com/v1/exercises?";

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

        String type = "", muscle = "", Difficulty = "", name="";
        Boolean isMultiple = false;
        Enumeration<String> paramList = request.getParameterNames();

        String complete_url = baseURL;

        while(paramList.hasMoreElements()){
            String TMP = paramList.nextElement();
            if(TMP.equals("type")){
                type = request.getParameter(TMP);

                if (isMultiple == true){
                    complete_url += "&";
                }else{
                    isMultiple = true;
                }
                complete_url += ("type="+type);
            }
            if(TMP.equals("muscle")){
                muscle = request.getParameter(TMP);
                if (isMultiple == true){
                    complete_url += "&";
                }else{
                    isMultiple = true;
                }
                complete_url += ("muscle="+muscle);
            }
            if(TMP.equals("Difficulty")){
                Difficulty = request.getParameter(TMP);
                if (isMultiple == true){
                    complete_url += "&";
                }else{
                    isMultiple = true;
                }
                complete_url += ("Difficulty="+Difficulty);
            }
            if(TMP.equals("name")){
                name = request.getParameter(TMP);
                if (isMultiple == true){
                    complete_url += "&";
                }else{
                    isMultiple = true;
                }
                complete_url += ("name="+name);
            }
        }

        URL url = new URL(complete_url);
        System.out.println("WORKOUT ADAPTER --> GET REQUEST: " + complete_url);

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
