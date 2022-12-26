package it.unitn.disi.sde.tmp.diet_workout.exercises;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Workout
{
    private String base_url = "https://api.api-ninjas.com/v1/exercises?";
    private String api_key = "zJRM87GPTK87aIS0eC41lQ==yT387tRyKsu98tkd";

    public String getExercises(String name, Utils.Types type, Utils.Muscles muscle, Utils.Difficulties difficulty)
    {
        String res = "ERROR";
        String complete_url = base_url;
        boolean add_separator = false;

        // CREATING REQUEST URL
        if (!name.equals("")){
            complete_url += "name=";
            complete_url += name;
            add_separator = true;
        }

        if(type != null){
            if (add_separator == true){
                complete_url += "&";
            }
            complete_url += "type=";
            complete_url += type.toString();
            add_separator = true;
        }

        if(muscle != null){
            if (add_separator == true){
                complete_url += "&";
            }
            complete_url += "muscle=";
            complete_url += muscle.toString();
            add_separator = true;
        }

        if(difficulty != null){
            if (add_separator == true){
                complete_url += "&";
            }
            complete_url += "difficulty=";
            complete_url += difficulty.toString();
        }

        // SEND REQUEST
        try
        {
            URL url = new URL(complete_url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("accept", "application/json");
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);// 5000 milliseconds = 5 seconds
            connection.setReadTimeout(5000);

            connection.addRequestProperty("x-api-key", api_key);

            // VARIABLES TO READ RESPONSE
            BufferedReader reader;
            String line;
            StringBuilder responseContent = new StringBuilder();

            // Test if the response from the server is successful
            int status = connection.getResponseCode();

            if (status >= 300) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
            else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
            res = responseContent.toString();
        }
        catch (MalformedURLException e)
        {
            throw new RuntimeException(e);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        return  res;
    }
}
