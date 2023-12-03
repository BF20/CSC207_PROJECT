
package use_cases.data_visualization;

import data_access.FileUserDataAccessObject;
import entity.Habit;
import entity.StudyHabitFactory;
import entity.UserFactory;
import entity.User;
import use_cases.data_visualization.DateUtils;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserJsDataTry {
    public static void main(String[] args) {
        String yamlPath = "Habit Builder Project/data/TestData.yaml"; // Replace with your YAML file path
        UserFactory userFactory = new UserFactory(); // Replace with your UserFactory creation logic
        StudyHabitFactory studyHabitFactory = new StudyHabitFactory(); // Replace with your StudyHabitFactory creation logic

        // Create an instance of FileUserDataAccessObject
        FileUserDataAccessObject userDataAccessObject;
        try {
            userDataAccessObject = new FileUserDataAccessObject(yamlPath, userFactory, studyHabitFactory);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        // Get the map of users
        Map<String, User> usersMap = userDataAccessObject.get_all_users();

        // Convert the map values (User objects) into a list of User objects
        List<User> userList = new ArrayList<>(usersMap.values());

        String outputDirectory = "Habit Builder Project/src/use_cases/data_visualization/users/";

        for (User user : userList) {
            System.out.println("User: " + user.GetUsername());



            List<Map<String, Object>> completedHabits = new ArrayList<>();
            int[] habitsHours = new int[7]; // Initialize with all zeroes

            for (Habit habit : user.GetAllCompletedHabits()) {
                System.out.println("Completed Habit: " + habit.time_spent + ", Date: " + habit.date);
                LocalDate habitDate = habit.date;

                // Check if the habit date is within the last seven days
                if (isWithinLastSevenDays(habitDate)) {
//                    Map<String, Object> habitMap = new HashMap<>();
//                    habitMap.put("time_spent", habit.time_spent);
//                    completedHabits.add(habitMap);

                    // Update the corresponding index in habitsHours
                    int index = getIndexFromHabitDate(habitDate);
                    habitsHours[index] += (int) habit.time_spent;
                }
            }

//            if (!completedHabits.isEmpty()) {
            writeHabitHoursToFile(user.GetUsername(), habitsHours, outputDirectory);

        }
    }

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

//package use_cases.data_visualization;
//
//import data_access.FileUserDataAccessObject;
//import entity.Habit;
//import entity.StudyHabitFactory;
//import entity.UserFactory;
//import entity.User;
//import use_cases.data_visualization.DateUtils;
//
//import java.io.*;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class UserJsDataTry {
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
//            List<Map<String, Object>> completedHabits = new ArrayList<>();
//
//            for (Habit habit : user.GetAllCompletedHabits()) {
//                LocalDate habitDate = habit.date;
//
//                // Check if the habit date is within the last seven days
//                if (isWithinLastSevenDays(habitDate)) {
//                    Map<String, Object> habitMap = new HashMap<>();
//                    habitMap.put("time_spent", habit.time_spent);
//                    completedHabits.add(habitMap);
//                }
//            }
//
//            if (!completedHabits.isEmpty()) {
//                writeHabitHoursToFile(user.GetUsername(), completedHabits, outputDirectory);
//            }
//        }
//    }
//
//    private static boolean isWithinLastSevenDays(LocalDate habitDate) {
//        DateUtils lastSevenDays = new DateUtils();
//        List<LocalDate> sevenDays = lastSevenDays.getLast7Days();
//        return sevenDays.contains(habitDate);
//    }
//
//    private static void writeHabitHoursToFile(String username, List<Map<String, Object>> completedHabits, String outputDirectory) {
//        String filePath = outputDirectory + username + "_habit_hours.js";
//
//        try (FileWriter jsFile = new FileWriter(filePath)) {
//            jsFile.write("const " + username + "_habit_hours = [");
//
//            for (int i = 0; i < completedHabits.size(); i++) {
//                double timeSpent = (double) completedHabits.get(i).get("time_spent");
//                jsFile.write(Double.toString(timeSpent));
//                if (i < completedHabits.size() - 1) {
//                    jsFile.write(", ");
//                }
//            }
//
//            jsFile.write("];");
//            System.out.println("JavaScript file generated for " + username);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}

//package use_cases.data_visualization;
//
//import entity.Habit;
//import entity.StudyHabitFactory;
//import entity.User;
//import entity.UserFactory;
//import data_access.FileUserDataAccessObject;
//import use_cases.data_visualization.DateUtils;
//
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//public class UserJsDataTry {
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
//        // Define the last seven days as LocalDate objects
//        DateUtils lastSevendays = new DateUtils();
//        List<LocalDate> sevenDays = lastSevendays.getLast7Days();
//
//        // Iterate through each user
//        for (User user : userList) {
//            System.out.println("Username: " + user.GetUsername());
//
//            // Initialize an array to store habit hours for the last seven days
//            int[] habitsHours = new int[7];
//
//            // Iterate through each completed habit of the user
//            for (Habit studyHabit : user.GetAllCompletedHabits()) {
//                LocalDate habitDate = studyHabit.date;
//
//                // Check if the habit date is within the last seven days
//                for (int i = 0; i < 7; i++) {
//                    if (habitDate.isEqual(sevenDays.get(i))) {
//                        habitsHours[i] += (int) studyHabit.time_spent; // Convert hours to integer
//                    }
//                }
//            }
//
//            // Print the habit hours for the last seven days
//            for (int i = 0; i < 7; i++) {
//                System.out.println(sevenDays.get(i) + ": " + habitsHours[i] + " hours");
//            }
//
//            // Write the habit hours as a JavaScript array to a file
//            writeHabitHoursToFile(user.GetUsername(), habitsHours);
//        }
//    }
//
//    private static void writeHabitHoursToFile(String username, int[] habitsHours) {
//        String directoryPath = "use_cases/data_visualization/users/";
//        File directory = new File(directoryPath);
//
//        // Create the directory if it doesn't exist
//        if (!directory.exists()) {
//            if (directory.mkdirs()) {
//                System.out.println("Directory created: " + directoryPath);
//            } else {
//                System.err.println("Failed to create directory: " + directoryPath);
//                return;
//            }
//        }
//
//        String filePath = directoryPath + username + "_habit_hours.js";
//        try (FileWriter fileWriter = new FileWriter(filePath)) {
//            fileWriter.write("const " + username + "_habit_hours = [" + String.join(", ", intArrayToStringArray(habitsHours)) + "];");
//            System.out.println("Habit hours written to file: " + filePath);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static String[] intArrayToStringArray(int[] intArray) {
//        String[] stringArray = new String[intArray.length];
//        for (int i = 0; i < intArray.length; i++) {
//            stringArray[i] = String.valueOf(intArray[i]);
//        }
//        return stringArray;
//    }
//}
//
//
////
////package use_cases.data_visualization;
////
////import entity.*;
////import data_access.FileUserDataAccessObject;
////import use_cases.data_visualization.DateUtils;
////
////import java.time.LocalDate;
////import java.util.ArrayList;
////import java.util.List;
////import java.util.Map;
////
////public class UserJsDataTry {
////    public static void main(String[] args) {
////        String yamlPath = "Habit Builder Project/data/TestData.yaml"; // Replace with your YAML file path
////        UserFactory userFactory = new UserFactory(); // Replace with your UserFactory creation logic
////        StudyHabitFactory studyHabitFactory = new StudyHabitFactory(); // Replace with your StudyHabitFactory creation logic
////
////        // Create an instance of FileUserDataAccessObject
////        FileUserDataAccessObject userDataAccessObject;
////        try {
////            userDataAccessObject = new FileUserDataAccessObject(yamlPath, userFactory, studyHabitFactory);
////        } catch (Exception e) {
////            e.printStackTrace();
////            return;
////        }
////
////        // Get the map of users
////        Map<String, User> usersMap = userDataAccessObject.get_all_users();
////
////        // Convert the map values (User objects) into a list of User objects
////        List<User> userList = new ArrayList<>(usersMap.values());
////
////        // Define the last seven days as LocalDate objects
////        DateUtils lastSevendays = new DateUtils();
////        List<LocalDate> sevenDays = lastSevendays.getLast7Days();
////
////        // Iterate through each user
////        for (User user : userList) {
////            System.out.println("Username: " + user.GetUsername());
////
////            // Initialize an array to store habit hours for the last seven days
////            int[] habitsHours = new int[7];
////
////            // Iterate through each completed habit of the user
////            for (Habit studyHabit : user.GetAllCompletedHabits()) {
////                LocalDate habitDate = studyHabit.date;
////
////                // Check if the habit date is within the last seven days
////                for (int i = 0; i < 7; i++) {
////                    if (habitDate.isEqual(sevenDays.get(i))) {
////                        habitsHours[i] += (int) studyHabit.time_spent; // Convert hours to integer
////                    }
////                }
////            }
////
////            // Print the habit hours for the last seven days
////            for (int i = 0; i < 7; i++) {
////                System.out.println(sevenDays.get(i) + ": " + habitsHours[i] + " hours");
////            }
////        }
////    }
////}
////
//////package use_cases.data_visualization;
//////
//////import entity.StudyHabitFactory;
//////import entity.User;
//////import entity.UserFactory;
//////import data_access.FileUserDataAccessObject;
//////
//////import java.util.ArrayList;
//////import java.util.List;
//////import java.util.Map;
//////
//////public class UserJsDataTry {
//////    public static void main(String[] args) {
//////        String yamlPath = "Habit Builder Project/data/TestData.yaml"; // Replace with your YAML file path
//////        UserFactory userFactory = new UserFactory(); // Replace with your UserFactory creation logic
//////        StudyHabitFactory studyHabitFactory = new StudyHabitFactory(); // Replace with your StudyHabitFactory creation logic
//////
//////        // Create an instance of FileUserDataAccessObject
//////        FileUserDataAccessObject userDataAccessObject;
//////        try {
//////            userDataAccessObject = new FileUserDataAccessObject(yamlPath, userFactory, studyHabitFactory);
//////        } catch (Exception e) {
//////            e.printStackTrace();
//////            return;
//////        }
//////
//////        // Get the map of users
//////        Map<String, User> usersMap = userDataAccessObject.get_all_users();
//////        System.out.println(usersMap);
//////
//////        // Convert the map values (User objects) into a list of User objects
//////        List<User> userList = new ArrayList<>(usersMap.values());
//////
//////
//////        DateUtils lastSevendays = new DateUtils();
//////        List<String> sevenDays = lastSevendays.getLast7Days();
//////
//////        int[] habitsHours = new int[7];
//////
//////        // Now you have a list of User objects
//////        for (User user : userList) {
//////            System.out.println("Username: " + user.GetUsername());
//////        }
//////    }
//////}
