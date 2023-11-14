package data_access;

import entity.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class DAOtest {
    /*
    Use this runnable class to test the DAO.
     */
    public static void main(String[] args) throws Exception {


        FileUserDataAccessObject dao = new FileUserDataAccessObject("Habit Builder Project/data/TestData.yaml", new UserFactory(), new StudyHabitFactory());

        dao.save();

        UserFactory userFactory = new UserFactory();

        // Add a new user called "Charlie"

        User user = userFactory.create("Charile", false, new ArrayList<>());

        dao.save_user(user);

        // Give Charlie a new habit:

        StudyHabitFactory studyHabitFactory = new StudyHabitFactory();

        study_habit new_habit = studyHabitFactory.create(2.0, LocalDate.parse("2023-10-01"), "Chemistry");

        user.AddHabit(new_habit);

        dao.save_user(user);

    }
}
