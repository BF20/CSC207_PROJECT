package use_cases.data_visualization;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserJSData {
    public static void main(String[] args) {
        Yaml yaml = new Yaml();
        try (FileInputStream fis = new FileInputStream("Habit Builder Project/data/TestData.yaml")) {
            List<Map<String, Object>> userData = yaml.load(fis);

            String outputDirectory = "Habit Builder Project/src/use_cases/data_visualization/users/";

            for (Map<String, Object> user : userData) {
                String username = (String) user.get("username");
                List<Map<String, Object>> completedHabits = (List<Map<String, Object>>) user.get("completed_habits");

                FileWriter jsFile = new FileWriter(outputDirectory + username + ".js");
                jsFile.write("const " + username + "_time_spent = [");


                DateUtils lastSevendays = new DateUtils();
                List<String> sevenDays = lastSevendays.getLast7Days();


                for (int i = 0; i < completedHabits.size(); i++) {
                    double timeSpent = (double) completedHabits.get(i).get("time_spent");
                    jsFile.write(Double.toString(timeSpent));
                    if (i < completedHabits.size() - 1) {
                        jsFile.write(", ");
                    }
                }

                jsFile.write("];");
                jsFile.close();

                System.out.println("JavaScript file generated for " + username);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public List getLastSevenDays() {
//
//
//    }
}

