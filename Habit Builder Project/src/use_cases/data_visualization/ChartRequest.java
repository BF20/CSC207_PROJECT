package use_cases.data_visualization;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChartRequest {

    public static void main(String[] args) {
        // Define the base URL for QuickChart
        String baseUrl = "https://quickchart.io/chart";

        // Specify the directory path where user JavaScript files are located
        String jsFilesDirectory = "Habit Builder Project/src/use_cases/data_visualization/users/";

        // Specify the directory path where graphs will be saved
        String graphsDirectory = "Habit Builder Project/src/use_cases/data_visualization/graphs/";

        // List of usernames for which to generate graphs
        String[] usernames = {"Bob", "Alice", "Charile"}; // Add more usernames as needed

        try {
            for (String username : usernames) {
                // Read the user's JavaScript file to get the time_spent data
                String jsFilePath = jsFilesDirectory + username + ".js";
                BufferedReader jsFileReader = new BufferedReader(new FileReader(jsFilePath));
                String jsData = jsFileReader.readLine(); // Read the JavaScript data

                // Extract the list of time_spent values using regular expressions
                String pattern = "const " + username + "_time_spent\\s*=\\s*\\[([^\\]]*)\\]";
                Pattern regex = Pattern.compile(pattern);
                Matcher matcher = regex.matcher(jsData);

                if (matcher.find()) {
                    String timeSpentData = matcher.group(1);
                    System.out.println("Time Spent Data for " + username + ": " + timeSpentData);

                    List<Double> timeSpentList = new ArrayList<>();
                    String[] values = timeSpentData.split(",");
                    for (String value : values) {
                        timeSpentList.add(Double.parseDouble(value.trim()));
                    }

                    // Now, you have a list of time_spent values as doubles
                    System.out.println("Time Spent List for " + username + ": " + timeSpentList);

                    String jsonPart = "{" +
                        "\"type\": \"bar\"," +
                        "\"data\": {" +
                        "\"labels\": [\"Day 1\", \"Day 2\", \"Day 3\", \"Day 4\", \"Day 5\", \"Day 6\", \"Day 7\"]," +
                        "\"datasets\": [{" +
                        "\"label\": \"Time Spent on Habits\"," +
                        "\"data\": " + timeSpentList + // Use the data from the user's JavaScript file
                        "}]" +
                        "}" +
                        "}";

                String encodedJsonPart = URLEncoder.encode(jsonPart, StandardCharsets.UTF_8.toString());

                // Define chart parameters
                String parameters = "backgroundColor=white&width=500&height=300&devicePixelRatio=1.0&format=png&version=2.9.3";

                // Construct the complete URL for the QuickChart API
                String completeUrl = baseUrl + "?chart=" + encodedJsonPart + "&" + parameters;

                URL url = new URL(completeUrl);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");

                int responseCode = con.getResponseCode();
                System.out.println("GET Response Code for " + username + ": " + responseCode);

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Save the PNG image in the graphs directory
                    String graphFilePath = graphsDirectory + username + "_chart.png";
                    InputStream inputStream = con.getInputStream();
                    FileOutputStream outputStream = new FileOutputStream(graphFilePath);

                    byte[] buffer = new byte[1024];
                    int bytesRead;

                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }

                    outputStream.close();
                    inputStream.close();
                    System.out.println("PNG image saved as " + graphFilePath);
                } else {
                    System.out.println("GET request for " + username + " not worked");
                }

                } else {
                    System.out.println("Time Spent Data not found for " + username);
                }

                jsFileReader.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//                // Construct the JSON data for the bar chart
//                String jsonPart = "{" +
//                        "\"type\": \"bar\"," +
//                        "\"data\": {" +
//                        "\"labels\": [\"Day 1\", \"Day 2\", \"Day 3\", \"Day 4\", \"Day 5\", \"Day 6\", \"Day 7\"]," +
//                        "\"datasets\": [{" +
//                        "\"label\": \"Time Spent on Habits\"," +
//                        "\"data\": " + jsData + // Use the data from the user's JavaScript file
//                        "}]" +
//                        "}" +
//                        "}";
//
//                String encodedJsonPart = URLEncoder.encode(jsonPart, StandardCharsets.UTF_8.toString());
//
//                // Define chart parameters
//                String parameters = "backgroundColor=white&width=500&height=300&devicePixelRatio=1.0&format=png&version=2.9.3";
//
//                // Construct the complete URL for the QuickChart API
//                String completeUrl = baseUrl + "?chart=" + encodedJsonPart + "&" + parameters;
//
//                URL url = new URL(completeUrl);
//                HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                con.setRequestMethod("GET");
//
//                int responseCode = con.getResponseCode();
//                System.out.println("GET Response Code for " + username + ": " + responseCode);
//
//                if (responseCode == HttpURLConnection.HTTP_OK) {
//                    // Save the PNG image in the graphs directory
//                    String graphFilePath = graphsDirectory + username + "_chart.png";
//                    InputStream inputStream = con.getInputStream();
//                    FileOutputStream outputStream = new FileOutputStream(graphFilePath);
//
//                    byte[] buffer = new byte[1024];
//                    int bytesRead;
//
//                    while ((bytesRead = inputStream.read(buffer)) != -1) {
//                        outputStream.write(buffer, 0, bytesRead);
//                    }
//
//                    outputStream.close();
//                    inputStream.close();
//                    System.out.println("PNG image saved as " + graphFilePath);
//                } else {
//                    System.out.println("GET request for " + username + " not worked");
//                }
            }


//import java.io.*;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//
//public class ChartRequest {
//
//    public static void main(String[] args) {
//        try {
//            String baseUrl = "https://quickchart.io/chart";
//            String jsonPart = "{"
//                    + "\"type\": \"bar\","
//                    + "\"data\": {"
//                    + "\"labels\": [\"Day 1 \", \"Day 2\", \"Day 3\", \"Day 4\", \"Day 5\", \"Day 6\", \"Day 7\", ],"
//                    + "\"datasets\": [{"
//                    + "\"label\": \"Time Spent on Habits\","
//                    +
//                    "\"data\": [4.0,2.0,6.0,1.0,8.0,12.0]"
//                    + "}]"
//                    + "}"
//                    + "}";
//            String encodedJsonPart = URLEncoder.encode(jsonPart, StandardCharsets.UTF_8.toString());
//
//            String parameters = "backgroundColor=white&width=500&height=300&devicePixelRatio=1.0&format=png&version=2.9.3";
//            String completeUrl = baseUrl + "?chart=" + encodedJsonPart + "&" + parameters;
//
//            URL url = new URL(completeUrl);
//            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.setRequestMethod("GET");
//
//            int responseCode = con.getResponseCode();
//            System.out.println("GET Response Code :: " + responseCode);
//
//            if (responseCode == HttpURLConnection.HTTP_OK) { // success
//                InputStream inputStream = con.getInputStream();
//                FileOutputStream outputStream = new FileOutputStream("chart.png");
//
//                byte[] buffer = new byte[1024];
//                int bytesRead;
//
//                while ((bytesRead = inputStream.read(buffer)) != -1) {
//                    outputStream.write(buffer, 0, bytesRead);
//                }
//
//                outputStream.close();
//                inputStream.close();
//                System.out.println("PNG image saved as chart.png");
//            } else {
//                System.out.println("GET request not worked");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}