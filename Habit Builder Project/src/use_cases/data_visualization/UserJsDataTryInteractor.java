
package use_cases.data_visualization;

import data_access.FileUserDataAccessObject;
import entity.Habit;
import entity.StudyHabitFactory;
import entity.UserFactory;
import entity.User;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserJsDataTryInteractor {

    public static void updateUserData() {
        String yamlPath = "Habit Builder Project/data/TestData.yaml";
        UserFactory userFactory = new UserFactory();
        StudyHabitFactory studyHabitFactory = new StudyHabitFactory();

        FileUserDataAccessObject userDataAccessObject;
        try {
            userDataAccessObject = new FileUserDataAccessObject(yamlPath, userFactory, studyHabitFactory);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        Map<String, User> usersMap = userDataAccessObject.get_all_users();
        List<User> userList = new ArrayList<>(usersMap.values());
        String outputDirectory = "Habit Builder Project/src/use_cases/data_visualization/users/";

        for (User user : userList) {
            System.out.println("User: " + user.GetUsername());
            int[] habitsHours = new int[7]; // Initialize with all zeroes
            for (Habit habit : user.GetAllCompletedHabits()) {
                LocalDate habitDate = habit.date;
                if (isWithinLastSevenDays(habitDate)) {
                    int index = getIndexFromHabitDate(habitDate);
                    habitsHours[index] += (int) habit.time_spent;
                }
            }
            writeHabitHoursToFile(user.GetUsername(), habitsHours, outputDirectory);
        }
    }
//    public static void main(String[] args) {
//        String yamlPath = "Habit Builder Project/data/TestData.yaml"; // Replace with your YAML file path
//        UserFactory userFactory = new UserFactory(); // Replace with your UserFactory creation logic
//        StudyHabitFactory studyHabitFactory = new StudyHabitFactory(); // Replace with your StudyHabitFactory creation logic
//
//        // Create an instance of FileUserDataAccessObject
//        FileUserDataAccessObject userDataAccessObject;
//        try {
//            userDataAccessObject = new FileUserDataAccessObject(yamlPath, userFactory, studyHabitFactory);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return;
//        }
//
//        // Get the map of users
//        Map<String, User> usersMap = userDataAccessObject.get_all_users();
//
//        // Convert the map values (User objects) into a list of User objects
//        List<User> userList = new ArrayList<>(usersMap.values());
//
//        String outputDirectory = "Habit Builder Project/src/use_cases/data_visualization/users/";
//
//        for (User user : userList) {
//            System.out.println("User: " + user.GetUsername());
//
//
//
//            List<Map<String, Object>> completedHabits = new ArrayList<>();
//            int[] habitsHours = new int[7]; // Initialize with all zeroes
//
//            for (Habit habit : user.GetAllCompletedHabits()) {
//                System.out.println("Completed Habit: " + habit.time_spent + ", Date: " + habit.date);
//                LocalDate habitDate = habit.date;
//
//                // Check if the habit date is within the last seven days
//                if (isWithinLastSevenDays(habitDate)) {
////                    Map<String, Object> habitMap = new HashMap<>();
////                    habitMap.put("time_spent", habit.time_spent);
////                    completedHabits.add(habitMap);
//
//                    // Update the corresponding index in habitsHours
//                    int index = getIndexFromHabitDate(habitDate);
//                    habitsHours[index] += (int) habit.time_spent;
//                }
//            }
//
////            if (!completedHabits.isEmpty()) {
//            writeHabitHoursToFile(user.GetUsername(), habitsHours, outputDirectory);
//
//        }


    private static boolean isWithinLastSevenDays(LocalDate habitDate) {
        DateUtils lastSevenDays = new DateUtils();
        List<LocalDate> sevenDays = lastSevenDays.getLast7Days();
        return sevenDays.contains(habitDate);
    }

    private static int getIndexFromHabitDate(LocalDate habitDate) {
        DateUtils lastSevenDays = new DateUtils();
        List<LocalDate> sevenDays = lastSevenDays.getLast7Days();
        for (int i = 0; i < sevenDays.size(); i++) {
            if (habitDate.equals(sevenDays.get(i))) {
                return i;
            }
        }
        return -1; // Return -1 if the habit date is not within the last seven days
    }

    private static void writeHabitHoursToFile(String username, int[] habitsHours, String outputDirectory) {
        String filePath = outputDirectory + username + "_habit_hours.js";

        try (FileWriter jsFile = new FileWriter(filePath)) {
            jsFile.write("const " + username + "_habit_hours = [");

            for (int i = 0; i < habitsHours.length; i++) {
                jsFile.write(Integer.toString(habitsHours[i]));
                if (i < habitsHours.length - 1) {
                    jsFile.write(", ");
                }
            }

            jsFile.write("];");
            System.out.println("JavaScript file generated for " + username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
