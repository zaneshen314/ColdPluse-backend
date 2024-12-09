package com.oocl.ita.web.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    public boolean sendEmailToSingleReceiver(String receiver) {
        try {
            // URL of the endpoint
            URL url = new URL("https://prod-13.southeastasia.logic.azure.com:443/workflows/e3a08f741f304a468295d853e4914542/triggers/manual/paths/invoke?api-version=2016-06-01&sp=%2Ftriggers%2Fmanual%2Frun&sv=1.0&sig=xm9I048rE2kU1mhxD8KeiIt7LplWS7AU_W6xWIB_1QE");
            callSendEmailWorkFlow(receiver, url);
            return true;

        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    private void callSendEmailWorkFlow(String receiver, URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set the request method to POST
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");

        // JSON payload
        String jsonInputString = String.format("{\"receiver\":\"%s\"}", receiver);

        // Send the request
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
    }

}
