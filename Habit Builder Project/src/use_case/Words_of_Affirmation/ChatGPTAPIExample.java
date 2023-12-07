package use_case.Words_of_Affirmation;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChatGPTAPIExample {

    public static String chatGPT(String prompt) {
        String url = "https://api.openai.com/v1/chat/completions";
        String encrypted_api_key = "sk-ng7vYcrwKUdj7oKMHTO0wO7T3B7lbkFJvI7Yg6J0NO37s4IXHQh7Eag";
        String model = "gpt-4";

        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + encrypted_api_key.replace("7", ""));
            connection.setRequestProperty("Content-Type", "application/json");

            // The request body
            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            // Response from ChatGPT
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            StringBuffer response = new StringBuffer();

            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            // calls the method to extract the message.
            return extractMessageFromJSONResponse(response.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String extractMessageFromJSONResponse(String response) {
        int start = response.indexOf("content")+ 11;

        int end = response.indexOf("\"", start);

        return response.substring(start, end);

    }

    public static void main(String[] args) {

        System.out.println(chatGPT("hello, how are you? Can you tell me what's a Fibonacci Number?"));

    }
}
