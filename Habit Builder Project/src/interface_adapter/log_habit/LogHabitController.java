package interface_adapter.log_habit;

import use_case.log_habit.LogHabitInteractor;
import use_case.log_habit.LogHabitInputData;

import java.time.LocalDate;

public class LogHabitController {

    final LogHabitInteractor logHabitInteractor;

    public LogHabitController(LogHabitInteractor logHabitInteractor) {
        this.logHabitInteractor = logHabitInteractor;
    }

    public void LogHabit(String username, double hours, LocalDate date, String subject) throws Exception {
        LogHabitInputData inputData = new LogHabitInputData(username, hours, date, subject);
        logHabitInteractor.LogHabit(inputData);
    }
}
