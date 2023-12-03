package use_case.log_habit;

import entity.Habit;
import entity.User;
import entity.study_habit;

//import java.time.LocalDate;

/**
 * This class is an interactor for logging habits in the application.
 * It implements the LogHabitInputBoundary interface and defines the business logic
 * for logging a user's habit.
 */
public class LogHabitInteractor implements LogHabitInputBoundary {
    private final LogHabitDataAccessInterface userDataAccessObject;
    private final LogHabitOutputBoundary logHabitOutputBoundary;

    /**
     * Constructs a new LogHabitInteractor with the given data access object.
     *
     * @param userDataAccessObject the data access object for user operations
     */
    public LogHabitInteractor(LogHabitDataAccessInterface userDataAccessObject, LogHabitOutputBoundary logHabitOutputBoundary) {
        this.userDataAccessObject = userDataAccessObject;
        this.logHabitOutputBoundary = logHabitOutputBoundary;
    }


    /**
     * Logs a habit for a user. Retrieves the user based on username, creates a new habit,
     * and then saves the user's updated information.
     *
     * @param inputData the data needed to log the habit
     * @throws Exception if the user is not found
     */
    @Override
    public void LogHabit(LogHabitInputData inputData) throws Exception {
        User user = userDataAccessObject.get_user(inputData.getUsername());
        if (user != null) {
            Habit newHabit = new study_habit(inputData.getHours(), inputData.getDate(), inputData.getSubject());
            user.AddHabit(newHabit);
            userDataAccessObject.save_user(user);
            logHabitOutputBoundary.ResetInputNumber();
        } else {
            throw new Exception("User not found");
        }
    }
}


