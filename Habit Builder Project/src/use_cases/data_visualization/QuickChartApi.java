//package use_cases.data_visualization;
//
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//import java.net.http.HttpRequest.BodyPublishers;
//import java.net.http.HttpResponse.BodyHandlers;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.util.List;
//import java.util.Map;
//
//import com.google.gson.Gson;
//
//public class QuickChartApi {
//    public static void main(String[] args) {
//       // String chartConfigJson = "{\"data:{\"datasets\":[{\"data\":[4,2,6,123,8,1234,987],\"label\":\"time spent\"}],\"labels\":[\"day1\",\"day2\",\"day3\",\"day4\",\"day5\",\"day6\",\"day7\"]},\"type\":\"bar\"}";
////         String chartConfigJson = "c={\"data:{\"datasets\":[{\"data\":[4,2,6,123,8,1234,987],\"label\":\"time spent\"}],\"labels\":[\"day1\",\"day2\",\"day3\",\"day4\",\"day5\",\"day6\",\"day7\"]},\"type\":\"bar\"}";
//
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("https://quickchart.io/"))
//                .header("Content-Type", "application/json")
//                .POST(HttpRequest.BodyPublishers.ofString(chartConfigJson))
//                .build();
//
//
//        try {
//            HttpResponse<java.io.InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
//
//            Path directoryPath = Paths.get("Habit Builder Project/src/use_cases/data_visualization/users");
//            Files.createDirectories(directoryPath);
//            Path filePath = directoryPath.resolve("chart.png");
//
//            System.out.println(response.body());
//
//
//            Files.copy(response.body(), filePath, StandardCopyOption.REPLACE_EXISTING);
//            System.out.println("Saved chart image to: " + filePath);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
