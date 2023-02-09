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
    private String ninjaBaseURL = "https://api.api-ninjas.com/v1/exercises?";

    // WGER-API
    private String wgerBaseURL = "https://wger.de/api/v2/exercise/?limit=10&language=2";

    // REQUEST PARAMETER
    private String type = "", muscle = "", difficulty = "", name = "";

    // SUPPORT CONST
    private final String ERROR = "ERROR";

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
        put(1, "Barbell");
        put(2, "SZ-Bar");
        put(3, "Dumbbell");
        put(4, "Gym mat");
        put(5, "Swiss Ball");
        put(6, "Pull-up bar");
        put(7, "None (Body)");
        put(8, "Bench");
        put(9, "Incline bench");
        put(10, "Kettlebell");
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
        // RESET PARAMETERS VARIABLE
        type = "";
        muscle = "";
        difficulty = "";
        name = "";

        // READ PARAMETERS & SETUP THE NINJA-API URL
        String ninjaCompleteUrl = handleParameters(request);

        // SETUP THE WGER URL
        String wgerCompleteUrl = getWgerUrl();

        // SEND FIRST REQUEST
        Boolean ninjaOK   = false;
        String  ninjaJSON = "";

        String ninjaResponse = sendRequest(ninjaCompleteUrl, true);
        if (ninjaResponse.equals(ERROR) == false){
            ninjaOK   = true;
            ninjaJSON = ninjaResponse;
        }

        // SEND SECOND REQUEST
        Boolean wgerOK   = false;
        String  wgerJSON = "";

        if (wgerCompleteUrl.equals(ERROR) == false)
        {
            String wgerResponse = sendRequest(wgerCompleteUrl, false);
            if (wgerResponse.equals(ERROR) == false){
                wgerOK   = true;
                wgerJSON = wgerResponse;
            }
        }

        // MENAGE RESPONSE CASES
        if (wgerOK == false && ninjaOK == false)
        {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print("SC_INTERNAL_SERVER_ERROR");
        }

        if (wgerOK == false && ninjaOK == true)
        {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            PrintWriter out = response.getWriter();
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
            String toSend = gson.toJson(ninjaJSON);
            out.print(toSend);
        }

        if (wgerOK == true && ninjaOK == false)
        {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // ADAPTING JSON RESPONSE FORMAT
            JSONArray responseArray = new JSONArray();
            responseArray = handleWgerArray(wgerJSON);
            String strToSend = responseArray.toString();

            // OK
            PrintWriter out = response.getWriter();
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
            String toSend = gson.toJson(strToSend);
            out.print(toSend);
        }

        if (wgerOK == true && ninjaOK == true)
        {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // SETUP ARRAYS
            JSONArray ninjaArray = new JSONArray(ninjaJSON);
            JSONArray wgerArray = new JSONArray();
            wgerArray = handleWgerArray(wgerJSON);

            // MERGING
            for (int i = 0; i < wgerArray.length(); i++)
            {
                ninjaArray.put(wgerArray.getJSONObject(i));
            }

            // SETUP RESPONSE
            String strToSend = ninjaArray.toString();
            PrintWriter out = response.getWriter();
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
            String toSend = gson.toJson(strToSend);
            out.print(toSend);
        }
    }

    private JSONArray handleWgerArray(String wgerJSON)
    {
        // SUPPORT VARIABLE
        JSONArray resArray = new JSONArray();

        // EXTRACT RESULTS ARRAY FROM THE RESPONSE
        JSONObject wgerObject = new JSONObject(wgerJSON);
        JSONArray wgerArray   = wgerObject.getJSONArray("results");

        // ITERATE TO USE AN UNIQUE JSON STANDARD
        for (int i = 0; i < wgerArray.length(); i++)
        {
            JSONObject explorer = wgerArray.getJSONObject(i);

            JSONObject TMP = new JSONObject();

            TMP.put("name", explorer.get("name"));

            if (type.equals("") == false)
            {
                TMP.put("type", type);
            } else
            {
                TMP.put("type", "unavailable");
            }

            if (muscle.equals("") == false)
            {
                TMP.put("muscle", muscle);
            } else
            {
                TMP.put("muscle", "muscle");
            }

            if (explorer.getJSONArray("equipment").length() == 0)
            {
                TMP.put("equipment", "unavailable");
            } else
            {
                TMP.put("equipment", wger_equipments.get(explorer.getJSONArray("equipment").get(0)));
            }

            TMP.put("difficulty", "unavailable");

            TMP.put("instructions", explorer.get("description"));

            resArray.put(TMP);
        }

        // OK
        return resArray;
    }

    private String sendRequest(String strUrl, Boolean isNinja) throws IOException
    {
        // SETUP HTTP CONNECTION
        System.out.println("WORKOUT ADAPTER --> GET REQUEST: " + strUrl);
        URL url  = new URL(strUrl);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestProperty("Accept", "application/json");
        if (isNinja)
        {
            http.setRequestProperty("x-api-key", x_api_key);
        }
        http.setReadTimeout(5000);
        System.out.println("RESPONSE CODE:" + http.getResponseCode());

        // HANDLE RESPONSE
        if (http.getResponseCode() == http.HTTP_OK)
        {
            // PARSE RESPONSE INTO JSON STRING
            BufferedReader br = new BufferedReader(new InputStreamReader(http.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null)
            {
                sb.append(line + "\n");
            }
            br.close();

            // CLOSE CONNECTION
            http.disconnect();

            // OK
            return sb.toString();
        }
        else
        {
            // CLOSE CONNECTION
            http.disconnect();

            // ERROR
            return ERROR;
        }
    }

    private String getWgerUrl()
    {
        // SUPPORT VARIABLES
        Integer wger_category = -1;
        String url = wgerBaseURL;

        // SETTING PARAMETERS
        if (muscle.equals("") == false)
        {
            wger_category = mapMuscleToCategory(muscle);
        }

        if (wger_category == -1 && type.equals("cardio"))
        {
            wger_category = wger_categories.get("Cardio");
        }

        // COMPLETE THE URL
        if (wger_category != -1)
        {
            url += "&category=" + wger_category;

            // OK
            return url;
        }
        else
        {
            // ERROR
            return ERROR;
        }
    }

    private String handleParameters(HttpServletRequest request)
    {
        // SUPPORT VARIABLES
        Boolean isMultiple = false;
        String urlToReturn = ninjaBaseURL;

        // GET REQUEST PARAMS LIST
        Enumeration<String> paramList = request.getParameterNames();

        // READING PARAMETERS AND COMPOSING URL
        while (paramList.hasMoreElements())
        {
            String TMP = paramList.nextElement();
            if (TMP.equals("type"))
            {
                type = request.getParameter(TMP);

                if (isMultiple == true)
                {
                    urlToReturn += "&";
                } else
                {
                    isMultiple = true;
                }
                urlToReturn += ("type=" + type);
            }
            if (TMP.equals("muscle"))
            {
                muscle = request.getParameter(TMP);
                if (isMultiple == true)
                {
                    urlToReturn += "&";
                } else
                {
                    isMultiple = true;
                }
                urlToReturn += ("muscle=" + muscle);
            }
            if (TMP.equals("Difficulty"))
            {
                difficulty = request.getParameter(TMP);
                if (isMultiple == true)
                {
                    urlToReturn += "&";
                } else
                {
                    isMultiple = true;
                }
                urlToReturn += ("Difficulty=" + difficulty);
            }
            if (TMP.equals("name"))
            {
                name = request.getParameter(TMP);
                if (isMultiple == true)
                {
                    urlToReturn += "&";
                } else
                {
                    isMultiple = true;
                }
                urlToReturn += ("name=" + name);
            }
        }
        return urlToReturn;
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
