package it.unitn.disi.sdeproject.extrafeature;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class ExtraFeature {

    public static String CreatePDF(int id, String jsonData, String type, PrintWriter printWriter)
    {
        StringBuffer content = new StringBuffer();
        try {
            URL url = new URL("http://localhost:8081/ExtraFeature_war_exploded/createPDF");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setReadTimeout(5000);

            Map<String, String> parameters = new HashMap<>();
            parameters.put("jsonData", jsonData);
            parameters.put("id", String.valueOf(id));
            parameters.put(type, "true");

            con.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(getParamsString(parameters));
            out.flush();
            out.close();

            int status = con.getResponseCode();
            System.out.println("Conn: " + status);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
                printWriter.print(inputLine);
            }
            in.close();

            con.disconnect();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return content.toString();
    }

    public static String CreatePDFWorkout(int workout_id, String jsonData, PrintWriter out)
    {
        return CreatePDF(workout_id, jsonData, "pdfworkout", out);
    }

    public static String CreatePDFDiet(int diet_id, String jsonData, PrintWriter out)
    {
        return CreatePDF(diet_id, jsonData, "pdfdiet", out);
    }

    public static void SendEmail(String athleteEmail, String emailSubject, String emailText) {
        try {
            URL url = new URL("http://localhost:8081/ExtraFeature_war_exploded/sendEmail");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setReadTimeout(5000);

            Map<String, String> parameters = new HashMap<>();
            parameters.put("athleteEmail", athleteEmail);
            parameters.put("emailSubject", emailSubject);
            parameters.put("emailText", emailText);

            con.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(con.getOutputStream());
            out.writeBytes(getParamsString(parameters));
            out.flush();
            out.close();

            int status = con.getResponseCode();
            System.out.println("Conn: " + status);
            con.disconnect();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return;
    }

    static String getParamsString(Map<String, String> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            result.append("&");
        }

        String resultString = result.toString();
        return resultString.length() > 0
                ? resultString.substring(0, resultString.length() - 1)
                : resultString;
    }

    public static void main(String[] args)
    {
        SendEmail("stefanofa2000@gmail.com", "Test", "Test");
    }
}
