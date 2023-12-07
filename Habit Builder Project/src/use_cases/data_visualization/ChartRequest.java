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
        String[] usernames = {"Bob", "Alice", "Charile"}; // add more usernames as needed
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
        String parameters = "backgroundColor=white&width=500&height=300&devicePixelRatio=2.0&format=png&version=2.9.3";
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


