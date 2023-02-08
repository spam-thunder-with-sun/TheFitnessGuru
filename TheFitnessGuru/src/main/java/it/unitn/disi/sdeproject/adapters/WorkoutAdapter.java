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
import java.util.HashMap;

@WebServlet(name = "WorkoutAdapter", value = "/home/WorkoutAdapter")
public class WorkoutAdapter extends HttpServlet
{
    // NINJA-API
    private String x_api_key = "zJRM87GPTK87aIS0eC41lQ==yT387tRyKsu98tkd";
    private String baseURL = "https://api.api-ninjas.com/v1/exercises?";

    // WGER API
    private String y_apy_key = "c223dcb883bce5e772b5d032817c56d612ace228";

    private HashMap<String, Integer> wger_categories = new HashMap<String, Integer>()
    {{
        put("Abs", 10);
        put("Arms", 8);
        put("Back", 12);
        put("Calves", 14);
        put("Cardio", 15);
        put("Chest", 11);
        put("Legs", 9);
        put("Shoulders", 13);
    }};

    private HashMap<Integer, String> wger_equipments = new HashMap<Integer, String>()
    {{
        put(1 ,"Barbell");
        put(2 ,"SZ-Bar");
        put(3 ,"Dumbbell");
        put(4 ,"Gym mat");
        put(5 ,"Swiss Ball");
        put(6 ,"Pull-up bar");
        put(7 ,"None (Body)");
        put(8 ,"Bench");
        put(9 ,"Incline bench");
        put(10,"Kettlebell");
    }};


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
        // READ PARAMETERS
        String type = "", muscle = "", Difficulty = "", name = "";
        Boolean isMultiple = false;
        Enumeration<String> paramList = request.getParameterNames();

        // SETTING FIRST GET REQUEST (to ninja-api)
        String ninja_JSON = "";
        Boolean ninja_OK = false;
        String complete_url = baseURL;

        while (paramList.hasMoreElements())
        {
            String TMP = paramList.nextElement();
            if (TMP.equals("type"))
            {
                type = request.getParameter(TMP);

                if (isMultiple == true)
                {
                    complete_url += "&";
                } else
                {
                    isMultiple = true;
                }
                complete_url += ("type=" + type);
            }
            if (TMP.equals("muscle"))
            {
                muscle = request.getParameter(TMP);
                if (isMultiple == true)
                {
                    complete_url += "&";
                } else
                {
                    isMultiple = true;
                }
                complete_url += ("muscle=" + muscle);
            }
            if (TMP.equals("Difficulty"))
            {
                Difficulty = request.getParameter(TMP);
                if (isMultiple == true)
                {
                    complete_url += "&";
                } else
                {
                    isMultiple = true;
                }
                complete_url += ("Difficulty=" + Difficulty);
            }
            if (TMP.equals("name"))
            {
                name = request.getParameter(TMP);
                if (isMultiple == true)
                {
                    complete_url += "&";
                } else
                {
                    isMultiple = true;
                }
                complete_url += ("name=" + name);
            }
        }

        // Send first request
        URL url = new URL(complete_url);
        System.out.println("WORKOUT ADAPTER --> GET REQUEST: " + complete_url);

        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestProperty("Accept", "application/json");
        http.setRequestProperty("x-api-key", x_api_key);
        http.setReadTimeout(5000);
        System.out.println("RESPONSE CODE:" + http.getResponseCode());

        // Response of first request
        if (http.getResponseCode() == http.HTTP_OK)
        {
            //Json parsing
            BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            br.close();
            // HANDLE FIRST JSON RESPONSE
            ninja_OK = true;
            ninja_JSON = sb.toString();
        }
        http.disconnect();

        // SETTING SECOND GET REQUEST (WGER-API)
        String wger_JSON = "";
        Boolean wger_OK = false;
        Integer wger_category = -1;
        if (muscle.equals("") == false)
        {
             wger_category = mapMuscleToCategory(muscle);
        }

        if (wger_category != -1 && type.equals("cardio")){
            wger_category = wger_categories.get("Cardio");
        }

        String base_wger_url = "https://wger.de/api/v2/exercise/?limit=10&language=2";

        if (wger_category != -1){
            base_wger_url += "&category="+wger_category;
        }

        URL wger_url = new URL(base_wger_url);
        System.out.println("WORKOUT ADAPTER --> GET REQUEST: " + wger_url);

        // SEND SECOND REQUEST
        HttpURLConnection http2 = (HttpURLConnection) wger_url.openConnection();
        http2.setRequestProperty("Accept", "application/json");
        http2.setReadTimeout(5000);
        System.out.println("RESPONSE CODE:" + http2.getResponseCode());

        // HANDLE SECOND RESPONSE
        if (http2.getResponseCode() == http2.HTTP_OK)
        {
            //Json parsing
            BufferedReader br = new BufferedReader(new InputStreamReader(http2.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            br.close();
            // HANDLE FIRST JSON RESPONSE
            wger_OK = true;

            wger_JSON = sb.toString();
        }
        http2.disconnect();


        // MENAGE AND MERGE JSON TO SEND
        if (wger_OK == false && ninja_OK == true){
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(ninja_JSON);
        }

        if (wger_OK == true && ninja_OK == false){
            // TODO
        }

        if (wger_OK == true && ninja_OK == true){
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // MERGE JSON
            JSONArray ninjaArray = new JSONArray(ninja_JSON);

            JSONObject wgerObject = new JSONObject(wger_JSON);
            JSONArray wgerArray = wgerObject.getJSONArray("results");
            for (int i = 0; i < wgerArray.length(); i++) {
                JSONObject explorer = wgerArray.getJSONObject(i);

                JSONObject TMP = new JSONObject();

                TMP.put("name", explorer.get("name"));

                if (type.equals("") == false){
                    TMP.put("type", type);
                }else{
                    TMP.put("type", "unavailable");
                }

                if (muscle.equals("") == false){
                    TMP.put("muscle", muscle);
                }else{
                    TMP.put("muscle", "muscle");
                }

                if (explorer.getJSONArray("equipment").length() == 0){
                    TMP.put("equipment", "unavailable");
                }else{
                    TMP.put("equipment", wger_equipments.get(explorer.getJSONArray("equipment").get(0)));
                }

                TMP.put("difficulty", "unavailable");

                TMP.put("instructions", explorer.get("description"));

                ninjaArray.put(TMP);
            }

            String strToSend = ninjaArray.toString();

            PrintWriter out = response.getWriter();
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
            String tosend = gson.toJson(strToSend);
            System.out.println(tosend);
            out.print(tosend);
        }

        if (wger_OK == false && ninja_OK == false){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print("SC_INTERNAL_SERVER_ERROR");
        }
    }

    private Integer mapMuscleToCategory(String muscle)
    {
        switch (muscle)
        {
            case "abdominals":
                return wger_categories.get("Abs");

            case "abductors":
            case "adductors":
            case "glutes":
            case "hamstrings":
            case "quadriceps":
                return wger_categories.get("Legs");

            case "biceps":
            case "triceps":
                return wger_categories.get("Arms");

            case "calves":
                return wger_categories.get("Calves");

            case "chest":
                return wger_categories.get("Chest");

            case "lats":
            case "lower_back":
            case "middle_back":
                return wger_categories.get("Back");

            case "neck":
            case "traps":
                return wger_categories.get("Shoulders");
        }

        return -1;
    }
}
