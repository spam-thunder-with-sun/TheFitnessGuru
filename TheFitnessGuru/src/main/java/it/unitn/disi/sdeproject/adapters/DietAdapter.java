package it.unitn.disi.sdeproject.adapters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

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
        // SETUP URLs
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

        URL url1 = new URL(complete_url);
        URL url2 = new URL(complete_url += "&offset=10");

        // SEND REQUESTS & HANDLE RESPONSE
        String res = SendRequests(url1, url2);
        if(res.equals("ERROR")){
            // ERROR RESPONSE
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print("SC_INTERNAL_SERVER_ERROR");
        }
        else{
            // JSON DATA RESPONSE
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            PrintWriter out = response.getWriter();
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
            String strToSend = gson.toJson(res);
            out.print(strToSend);
        }
    }

    private String SendRequests(URL url1, URL url2) throws IOException
    {
        // SETUP FIRST REQUEST
        HttpURLConnection http1 = (HttpURLConnection)url1.openConnection();
        http1.setRequestProperty("Accept", "application/json");
        http1.setRequestProperty("x-api-key", x_api_key);
        http1.setReadTimeout(5000);
        System.out.println("DIET ADAPTER --> GET REQUEST: " + url1);
        System.out.println("RESPONSE CODE:" + http1.getResponseCode());

        // SEND REQUESTS
        if (http1.getResponseCode() == http1.HTTP_OK)
        {
            // PARSE RESPONSE INTO JSON
            BufferedReader br1 = new BufferedReader(new InputStreamReader(http1.getInputStream()));
            StringBuilder sb1 = new StringBuilder();
            String line1;
            while ((line1 = br1.readLine()) != null) {
                sb1.append(line1+"\n");
            }
            br1.close();
            String jsonResponse1 = sb1.toString();

            // CLOSE CONNECTION
            http1.disconnect();

            // SETUP SECOND REQUEST STRING
            HttpURLConnection http2 = (HttpURLConnection)url2.openConnection();
            http2.setRequestProperty("Accept", "application/json");
            http2.setRequestProperty("x-api-key", x_api_key);
            http2.setReadTimeout(5000);
            System.out.println("DIET ADAPTER --> GET REQUEST: " + url2);
            System.out.println("RESPONSE CODE:" + http2.getResponseCode());

            if (http2.getResponseCode() == http2.HTTP_OK)
            {
                // PARSE RESPONSE INTO JSON STRING
                BufferedReader br2 = new BufferedReader(new InputStreamReader(http2.getInputStream()));
                StringBuilder sb2 = new StringBuilder();
                String line2;
                while ((line2 = br2.readLine()) != null)
                {
                    sb2.append(line2 + "\n");
                }
                br2.close();

                // CLOSE CONNECTION
                String jsonResponse2 = sb2.toString();

                http2.disconnect();
                // RETURN MERGED JSON
                return mergeJSON(jsonResponse1, jsonResponse2);
            }
            else{
                return "ERROR";
            }
        }else{
            return "ERROR";
        }
    }

    private String mergeJSON(String jsonResponse1, String jsonResponse2)
    {
        // SETUP
        JSONArray resArray = new JSONArray(jsonResponse1);
        JSONArray arrayToMerge = new JSONArray(jsonResponse2);

        // MERGING
        for (int i = 0; i < arrayToMerge.length(); i++) {
            JSONObject objToAdd = arrayToMerge.getJSONObject(i);
            resArray.put(objToAdd);
        }

        // OK
        return resArray.toString();
    }
}
