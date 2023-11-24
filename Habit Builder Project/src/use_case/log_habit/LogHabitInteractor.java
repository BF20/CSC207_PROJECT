package use_case.log_habit;

import data_access.FileUserDataAccessObject;
import entity.Habit;
import entity.User;
import entity.study_habit;

import java.time.LocalDate;

public class LogHabitInteractor implements LogHabitUseCase {
    private final FileUserDataAccessObject userDataAccessObject;

    public LogHabitInteractor(FileUserDataAccessObject userDataAccessObject) {
        this.userDataAccessObject = userDataAccessObject;
    }

    @Override
    public void LogHabit(String username, double hours, LocalDate date, String subject) throws Exception {
        User user = userDataAccessObject.get_user(username);
        if (user != null) {
            Habit newHabit = new study_habit(hours, date, subject);
            user.AddHabit(newHabit);
            userDataAccessObject.save_user(user);
        } else {
            throw new Exception("User not found");
        }
    }
}


