package com.oocl.ita.web.core.email;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class EmailUtil {

    public static void testEmail(){
        Map<String,String> params = new HashMap<>();
        params.put("receiver", "zane.shen@oocl.com");
        try {
            callSendEmailWorkFlow(params, EmailType.EMAIL_FLOW);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void callSendEmailWorkFlow(Map<String, String> params, EmailType emailType) throws IOException {

        URL urlInstance = new URL(emailType.getUrl());
        HttpURLConnection connection = (HttpURLConnection) urlInstance.openConnection();

        // Set the request method to POST
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");

        // Construct JSON payload
        StringBuilder jsonInputString = new StringBuilder("{");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            jsonInputString.append(String.format("\"%s\":\"%s\",", entry.getKey(), entry.getValue()));
        }
        // Remove the trailing comma and close the JSON object
        jsonInputString.deleteCharAt(jsonInputString.length() - 1).append("}");

        // Send the request
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
    }

    public static void main(String[] args) {

        testEmail();
    }
}
