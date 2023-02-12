package it.unitn.disi.sdeproject.extrafeature;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class ExtraFeature {
    public static void SendEmail(String athleteEmail, String emailSubject, String emailText) throws IOException {

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
        try {
            SendEmail("stefanofa2000@gmail.com", "Test", "Test");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
