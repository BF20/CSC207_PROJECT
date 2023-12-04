package use_cases.data_visualization;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChartRequest {

    private static final String JS_FILES_DIRECTORY = "Habit Builder Project/src/use_cases/data_visualization/users/";
    private static final String GRAPHS_DIRECTORY = "Habit Builder Project/src/use_cases/data_visualization/graphs/";
    private static final String BASE_URL = "https://quickchart.io/chart";

    public static void main(String[] args) {
        String[] usernames = {"Bob", "Alice", "Charile"}; // Add more usernames as needed
        for (String username : usernames) {
            try {
                String timeSpentData = readTimeSpentData(username);
                System.out.println(timeSpentData);
                List<Double> timeSpentList = parseTimeSpentData(timeSpentData);
                System.out.println(timeSpentList);
                String jsonPart = buildChartJson(timeSpentList);
                System.out.println(jsonPart);
                String encodedJsonPart = encodeChartJson(jsonPart);
                System.out.println(encodedJsonPart);
                String chartUrl = constructChartUrl(encodedJsonPart);
                System.out.println(chartUrl);
                fetchAndSaveChartImage(username, chartUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String readTimeSpentData(String username) throws IOException {
        String jsFilePath = JS_FILES_DIRECTORY + username + "_habit_hours.js";
        BufferedReader jsFileReader = new BufferedReader(new FileReader(jsFilePath));
        String jsData = jsFileReader.readLine();
        jsFileReader.close();

        String pattern = "const " + username + "_habit_hours\\s*=\\s*\\[([^\\]]*)\\]";
        Matcher matcher = Pattern.compile(pattern).matcher(jsData);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new IOException("Time spent data not found for " + username);
        }
    }

    public static List<Double> parseTimeSpentData(String data) {
        List<Double> timeSpentList = new ArrayList<>();
        String[] values = data.split(",");
        for (String value : values) {
            timeSpentList.add(Double.parseDouble(value.trim()));
        }
        Collections.reverse(timeSpentList);
        return timeSpentList;
    }

    public static String buildChartJson(List<Double> timeSpentList) {
        return "{" +
                "\"type\": \"bar\"," +
                "\"data\": {" +
                "\"labels\": [\"Day 1\", \"Day 2\", \"Day 3\", \"Day 4\", \"Day 5\", \"Day 6\", \"Day 7\"]," +
                "\"datasets\": [{" +
                "\"label\": \"Time Spent on Habits\"," +
                "\"data\": " + timeSpentList +
                "}]" +
                "}}";
    }

    public static String encodeChartJson(String jsonPart) throws UnsupportedEncodingException {
        return URLEncoder.encode(jsonPart, StandardCharsets.UTF_8);
    }

    public static String constructChartUrl(String encodedJsonPart) {
        String parameters = "backgroundColor=white&width=500&height=300&devicePixelRatio=1.0&format=png&version=2.9.3";
        return BASE_URL + "?chart=" + encodedJsonPart + "&" + parameters;
    }

    public static void fetchAndSaveChartImage(String username, String chartUrl) throws IOException {
        URL url = new URL(chartUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            String graphFilePath = GRAPHS_DIRECTORY + username + "_chart.png";
            try (InputStream inputStream = con.getInputStream();
                 FileOutputStream outputStream = new FileOutputStream(graphFilePath)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
            System.out.println("Chart image saved: " + graphFilePath);
        } else {
            throw new IOException("Error fetching chart image: " + responseCode);
        }
    }
}


//package use_cases.data_visualization;
//
//import java.io.*;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class ChartRequest {
//    private static final String JS_FILES_DIRECTORY = "Habit Builder Project/src/use_cases/data_visualization/users/";
//    private static final String GRAPHS_DIRECTORY = "Habit Builder Project/src/use_cases/data_visualization/graphs/";
//
//    public static void main(String[] args) {
//        String testUsername = "Bob"; // Replace with a valid username for testing
//
//        // Test getTimeSpentDataForUser
//        String timeSpentData = null;
//        try {
//            timeSpentData = ChartRequest.getTimeSpentDataForUser(testUsername);
//            System.out.println("Time spent data for user " + testUsername + ": " + timeSpentData);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // Test parseTimeSpentData
//        if (timeSpentData != null) {
//            List<Double> timeSpentList = ChartRequest.parseTimeSpentData(timeSpentData);
//            System.out.println("Parsed time spent list: " + timeSpentList);
//        }
//
//        // Test buildChartJson
//        if (timeSpentData != null) {
//            List<Double> timeSpentList = ChartRequest.parseTimeSpentData(timeSpentData);
//            String chartJson = ChartRequest.buildChartJson(timeSpentList);
//            System.out.println("Chart JSON: " + chartJson);
//        }
//
//        // Test buildChartUrl
//        if (timeSpentData != null) {
//            try {
//                List<Double> timeSpentList = ChartRequest.parseTimeSpentData(timeSpentData);
//                String chartUrl = ChartRequest.buildChartUrl(timeSpentList);
//                System.out.println("Chart URL: " + chartUrl);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        // Test fetchAndSaveChartImage
//        if (timeSpentData != null) {
//            try {
//                List<Double> timeSpentList = ChartRequest.parseTimeSpentData(timeSpentData);
//                String chartUrl = ChartRequest.buildChartUrl(timeSpentList);
//                ChartRequest.fetchAndSaveChartImage(testUsername, chartUrl);
//                System.out.println("Chart image should be saved for user " + testUsername);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static void generateChartForUser(String username) {
//        try {
//            String timeSpentData = getTimeSpentDataForUser(username);
//            if (timeSpentData == null) {
//                return; // Time spent data not found or other error occurred
//            }
//
//            List<Double> timeSpentList = parseTimeSpentData(timeSpentData);
//            String chartUrl = buildChartUrl(timeSpentList);
//            fetchAndSaveChartImage(username, chartUrl);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static String getTimeSpentDataForUser(String username) throws IOException {
//        String jsFilePath = JS_FILES_DIRECTORY + username + "_habit_hours.js";
//        File jsFile = new File(jsFilePath);
//        if (!jsFile.exists()) {
//            System.err.println("JS file does not exist: " + jsFilePath);
//            return null;
//        }
//
//        BufferedReader jsFileReader = new BufferedReader(new FileReader(jsFile));
//        String jsData = jsFileReader.readLine();
//        jsFileReader.close();
//
//        String pattern = "const " + username + "_habit_hours\\s*=\\s*\\[([^\\]]*)\\]";
//        Pattern regex = Pattern.compile(pattern);
//        Matcher matcher = regex.matcher(jsData);
//
//        if (matcher.find()) {
//            return matcher.group(1);
//        } else {
//            System.err.println("Could not find habit hours data in JS file.");
//            return null;
//        }
//    }
//
//    private static List<Double> parseTimeSpentData(String timeSpentData) {
//        List<Double> timeSpentList = new ArrayList<>();
//        String[] values = timeSpentData.split(",");
//        for (String value : values) {
//            timeSpentList.add(Double.parseDouble(value.trim()));
//        }
//        return timeSpentList;
//    }
//
//    private static String buildChartUrl(List<Double> timeSpentList) throws UnsupportedEncodingException {
//        String jsonPart = buildChartJson(timeSpentList);
//        String encodedJsonPart = URLEncoder.encode(jsonPart, StandardCharsets.UTF_8);
//        String parameters = "backgroundColor=white&width=500&height=300&devicePixelRatio=1.0&format=png&version=2.9.3";
//        return "https://quickchart.io/chart" + "?chart=" + encodedJsonPart + "&" + parameters;
//    }
//
//    private static String buildChartJson(List<Double> timeSpentList) {
//        return "{" +
//                "\"type\": \"bar\","
//                + "\"data\": {"
//                + "\"labels\": [\"Day 1\", \"Day 2\", \"Day 3\", \"Day 4\", \"Day 5\", \"Day 6\", \"Day 7\"],"
//                + "\"datasets\": [{"
//                + "\"label\": \"Time Spent on Habits\","
//                + "\"data\": "
//                + timeSpentList +
//                "}]" +
//                "}";
//    }
//
//    private static void fetchAndSaveChartImage(String username, String chartUrl) throws IOException {
//        URL url = new URL(chartUrl);
//        HttpURLConnection con = (HttpURLConnection) url.openConnection();
//        con.setRequestMethod("GET");
//
//        int responseCode = con.getResponseCode();
//        if (responseCode != HttpURLConnection.HTTP_OK) {
//            System.err.println("Error fetching chart image: " + responseCode);
//            return;
//        }
//
//        String graphFilePath = GRAPHS_DIRECTORY + username + "_chart.png";
//        try (InputStream inputStream = con.getInputStream();
//             FileOutputStream outputStream = new FileOutputStream(graphFilePath)) {
//
//            byte[] buffer = new byte[1024];
//            int bytesRead;
//            while ((bytesRead = inputStream.read(buffer)) != -1) {
//                outputStream.write(buffer, 0, bytesRead);
//            }
//        }
//        System.out.println("Chart image saved: " + graphFilePath);
//    }
//}
//
////package use_cases.data_visualization;
////
////import java.io.*;
////import java.net.HttpURLConnection;
////import java.net.URL;
////import java.net.URLEncoder;
////import java.nio.charset.StandardCharsets;
////import java.util.ArrayList;
////import java.util.List;
////import java.util.regex.Matcher;
////import java.util.regex.Pattern;
////
////public class ChartRequest {
////    private static final String JS_FILES_DIRECTORY = "Habit Builder Project/src/use_cases/data_visualization/users/";
////    private static final String GRAPHS_DIRECTORY = "Habit Builder Project/src/use_cases/data_visualization/graphs/";
////
////    public static void main(String[] args) {
////        String testUsername = "Alice"; // Replace with a valid username
////
////        // Call the method to generate the chart for the user
////        ChartRequest.generateChartForUser(testUsername);
////
////        // After calling the method, check if the file was created
////        String graphFilePath = ChartRequest.GRAPHS_DIRECTORY + testUsername + "_chart.png";
////        File file = new File(graphFilePath);
////        if (file.exists() && file.length() > 0) {
////            System.out.println("Chart generated successfully. File path: " + graphFilePath);
////        } else {
////            System.out.println("Chart generation failed or file is empty.");
////        }
////    }
////    public static void generateChartForUser(String username) {
////        String baseUrl = "https://quickchart.io/chart";
////
////        try {
////            String jsFilePath = JS_FILES_DIRECTORY + username + "_habit_hours.js";
////            BufferedReader jsFileReader = new BufferedReader(new FileReader(jsFilePath));
////            String jsData = jsFileReader.readLine();
////
////            String pattern = "const " + username + "_habit_hours\\s*=\\s*\\[([^\\]]*)\\]";
////            Pattern regex = Pattern.compile(pattern);
////            Matcher matcher = regex.matcher(jsData);
////
////            if (matcher.find()) {
////                String timeSpentData = matcher.group(1);
////
////                List<Double> timeSpentList = new ArrayList<>();
////                String[] values = timeSpentData.split(",");
////                for (String value : values) {
////                    timeSpentList.add(Double.parseDouble(value.trim()));
////                }
////
////
////                String jsonPart = "{" +
////                        "\"type\": \"bar\"," +
////                        "\"data\": {" +
////                        "\"labels\": [\"Day 1\", \"Day 2\", \"Day 3\", \"Day 4\", \"Day 5\", \"Day 6\", \"Day 7\"]," +
////                        "\"datasets\": [{" +
////                        "\"label\": \"Time Spent on Habits\"," +
////                        "\"data\": " + timeSpentList +
////                        "}]" +
////                        "}";
////
////                System.out.println(timeSpentList);
////
////                String encodedJsonPart = URLEncoder.encode(jsonPart, StandardCharsets.UTF_8.toString());
////
////                String parameters = "backgroundColor=white&width=500&height=300&devicePixelRatio=1.0&format=png&version=2.9.3";
////                String completeUrl = baseUrl + "?chart=" + encodedJsonPart + "&" + parameters;
////                System.out.println(completeUrl);
////
////                URL url = new URL(completeUrl);
////                HttpURLConnection con = (HttpURLConnection) url.openConnection();
////                con.setRequestMethod("GET");
////
////                int responseCode = con.getResponseCode();
////
////                if (responseCode == HttpURLConnection.HTTP_OK) {
////                    String graphFilePath = GRAPHS_DIRECTORY + username + "_chart.png";
////                    System.out.println(graphFilePath);
////                    InputStream inputStream = con.getInputStream();
////                    FileOutputStream outputStream = new FileOutputStream(graphFilePath);
////                    System.out.println(outputStream);
////
////                    byte[] buffer = new byte[1024];
////                    int bytesRead;
////                    while ((bytesRead = inputStream.read(buffer)) != -1) {
////                        outputStream.write(buffer, 0, bytesRead);
////                    }
////                    outputStream.close();
////                    inputStream.close();
////                    System.out.println("Chart image saved: " + graphFilePath);
////
////                    // Additional check to see if the file exists and has content
////                    File file = new File(graphFilePath);
////                    if(file.exists() && file.length() > 0) {
////                        System.out.println("File exists and is not empty");
////                    } else {
////                        System.out.println("File does not exist or is empty");
////                    }
////                }
////
////
////            }
////
////            jsFileReader.close();
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////    }
////}
