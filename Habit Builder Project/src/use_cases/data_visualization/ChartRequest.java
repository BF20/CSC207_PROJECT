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
    private static final String JS_FILES_DIRECTORY = "Habit Builder Project/src/use_cases/data_visualization/users/";
    private static final String GRAPHS_DIRECTORY = "Habit Builder Project/src/use_cases/data_visualization/graphs/";

    public static void generateChartForUser(String username) {
        String baseUrl = "https://quickchart.io/chart";

        try {
            String jsFilePath = JS_FILES_DIRECTORY + username + "_habit_hours.js";
            BufferedReader jsFileReader = new BufferedReader(new FileReader(jsFilePath));
            String jsData = jsFileReader.readLine();

            String pattern = "const " + username + "_habit_hours\\s*=\\s*\\[([^\\]]*)\\]";
            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(jsData);

            if (matcher.find()) {
                String timeSpentData = matcher.group(1);

                List<Double> timeSpentList = new ArrayList<>();
                String[] values = timeSpentData.split(",");
                for (String value : values) {
                    timeSpentList.add(Double.parseDouble(value.trim()));
                }


                String jsonPart = "{" +
                        "\"type\": \"bar\"," +
                        "\"data\": {" +
                        "\"labels\": [\"Day 1\", \"Day 2\", \"Day 3\", \"Day 4\", \"Day 5\", \"Day 6\", \"Day 7\"]," +
                        "\"datasets\": [{" +
                        "\"label\": \"Time Spent on Habits\"," +
                        "\"data\": " + timeSpentList +
                        "}]" +
                        "}";

                System.out.println(timeSpentList);

                String encodedJsonPart = URLEncoder.encode(jsonPart, StandardCharsets.UTF_8.toString());

                String parameters = "backgroundColor=white&width=500&height=300&devicePixelRatio=1.0&format=png&version=2.9.3";
                String completeUrl = baseUrl + "?chart=" + encodedJsonPart + "&" + parameters;

                URL url = new URL(completeUrl);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");

                int responseCode = con.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    String graphFilePath = GRAPHS_DIRECTORY + username + "_chart.png";
                    InputStream inputStream = con.getInputStream();
                    FileOutputStream outputStream = new FileOutputStream(graphFilePath);

                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }

                    outputStream.close();
                    inputStream.close();
                }

            }

            jsFileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
