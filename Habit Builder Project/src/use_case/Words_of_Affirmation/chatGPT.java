package use_case.Words_of_Affirmation;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class chatGPT {


    public static String chat(String prompt) throws IOException {

        URL url = new URL("https://api.openai.com/v1/engines/gpt-3.5-turbo/completions");

        // Open a connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set the request method to POST
        connection.setRequestMethod("POST");
        String encrypted_api_key = "sk-ng7vYcrwKUdj7oKMHTO0wO7T3B7lbkFJvI7Yg6J0NO37s4IXHQh7Eag";
        // Set the request headers
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + encrypted_api_key.replace("7", "")); // Replace with your API key

        // Enable input and output streams
        connection.setDoOutput(true);

        // Define the data you want to send in the POST request
        String data = "{"
                + "\"model\": \"gpt-3.5-turbo\","
                + "\"prompt\": \"" + prompt + "\","
                + "\"max_tokens\": 4000"
                + "}";

        // Send the POST request
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = data.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // Read the response
        try (java.io.BufferedReader br = new java.io.BufferedReader(
                new java.io.InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
            return response.toString();

        }


    }

    public static void main(String[] args) throws Exception {
        chat("what is love?");
    }


}
