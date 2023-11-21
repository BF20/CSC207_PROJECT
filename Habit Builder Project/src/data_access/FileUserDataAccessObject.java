package data_access;

import java.io.*;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.*;


import entity.*;
import org.yaml.snakeyaml.Yaml;
import use_case.mainwindow.MainWindowDataAccessInterface;

public class FileUserDataAccessObject implements MainWindowDataAccessInterface {

    private final File YAML_File;

    private final Map<String, User> users = new HashMap<>();

    private UserFactory userFactory;

    private StudyHabitFactory studyHabitFactory;

    public FileUserDataAccessObject(String YAML_Path, UserFactory userFactory, StudyHabitFactory studyHabitFactory) throws Exception {
        this.userFactory = userFactory;
        this.studyHabitFactory = studyHabitFactory;

        YAML_File = new File(YAML_Path);



        if (YAML_File.length() == 0) {
            save();
        } else {

            try (InputStream input = new FileInputStream(YAML_File)) {

                // Here we simply take the data from the file, put it into some classes
                // and shove it all into the accounts attribute.

                Yaml yaml = new Yaml();

                // We load the data types that we can. The rest of the data types
                // we will cast later.
                List<Map<String, Object>> data = yaml.load(input);

                for (Map<String, Object> user: data) {
                    // Putting this data into the "user" class
                    String username = (String) user.get("username");
                    boolean admin = (Boolean) user.get("admin");
                    List<Map<String, Object>> completed_habits = (List<Map<String, Object>>) user.get("completed_habits");

                    // List to put all the habits into
                    ArrayList<Habit> completed_habits_unpacked = new ArrayList<>();

                    // Putting all the habits into "completed habits"
                    for (Map<String, Object> habit: completed_habits) {
                        String habit_type = (String) habit.get("habit_type");
                        double time_spent = (double) habit.get("time_spent");
                        LocalDate date = LocalDate.parse((String) habit.get("date"));
                        String subject = (String) habit.get("subject");

                        // assign to the correct habit type
                        if (Objects.equals(habit_type, "study_habit")) {
                            study_habit constructed_habit = studyHabitFactory.create(time_spent, date, subject);
                            completed_habits_unpacked.add(constructed_habit);
                        }

                        else {
                            throw new Exception("Habit type specified in YAML file not supported!");

                        }
                    }

                    // Constructing the user
                    User constructed_user = userFactory.create(username, admin, completed_habits_unpacked);

                    // Adding the constructed user to the "users" attribute
                    users.put(username, constructed_user);

                }

            }
        }
    }

    @Override
    public Map<String, User> get_all_users() {
        return users;
    }

    @Override
    public User get_user(String username) {
        return users.get(username);
    }

    @Override
    public void save_user(User user) throws Exception {
        users.put(user.GetUsername(), user);
        this.save();

    }

    public void save() throws Exception {

        List<Object> data = new ArrayList<>();

        for (User user: this.users.values()) {
            Map<String, Object> user_map = new LinkedHashMap<>();


            List<Map<String, Object>> completed_habits = new ArrayList<>();

            ArrayList<Habit> habits = user.GetAllCompletedHabits();

            for (Habit habit:habits) {
                Map<String, Object> habit_map = new LinkedHashMap<>();

                if (habit.getClass() == study_habit.class) {
                    habit_map.put("habit_type", "study_habit");
                    habit_map.put("subject", ((study_habit) habit).subject);
                } else {
                    throw new Exception("Error: unknown habit type!");
                }

                habit_map.put("time_spent", habit.time_spent);
                habit_map.put("date", habit.date.toString());


                completed_habits.add(habit_map);

            }

            user_map.put("username", user.GetUsername());
            user_map.put("admin", user.GetAdminStatus());
            user_map.put("completed_habits", completed_habits);
            data.add(user_map);

        }


        Yaml yaml = new Yaml();
        FileWriter writer = new FileWriter(YAML_File);
        yaml.dump(data, writer);

    }

}





