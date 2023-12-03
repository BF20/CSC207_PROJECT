package interface_adapter.log_habit;

import use_case.log_habit.LogHabitInteractor;
import use_case.log_habit.LogHabitInputData;

import java.time.LocalDate;

/**
 * This class acts as a controller for the log habit functionality in the application.
 * It bridges the user interface and the business logic for logging habits.
 * It relies on the LogHabitInteractor to process the habit logging.
 */
public class LogHabitController {

    final LogHabitInteractor logHabitInteractor;


    /**
     * Constructs a new LogHabitController with the specified LogHabitInteractor.
     *
     * @param logHabitInteractor the interactor responsible for handling log habit business logic
     */
    public LogHabitController(LogHabitInteractor logHabitInteractor) {
        this.logHabitInteractor = logHabitInteractor;
    }

    /**
     * Logs a habit for a given user. This method creates a LogHabitInputData object
     * and passes it to the LogHabitInteractor.
     *
     * @param username the username of the user who is logging the habit
     * @param hours    the number of hours spent on the habit
     * @param date     the date on which the habit was performed
     * @param subject  the subject or area of the habit
     * @throws Exception if there is an issue logging the habit (e.g., user not found)
     */
    public void LogHabit(String username, double hours, LocalDate date, String subject) throws Exception {
        LogHabitInputData inputData = new LogHabitInputData(username, hours, date, subject);
        logHabitInteractor.LogHabit(inputData);
    }
}
