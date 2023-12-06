package interface_adapter.log_habit;

import use_case.log_habit.LogHabitOutputBoundary;
import view.LogHabit.LogHabitViewModel;

/**
 * Presenter for the Log Habit use case. This class acts as an intermediary between the use case (business logic)
 * and the view model (user interface logic). It implements the LogHabitOutputBoundary interface to
 * respond to output from the use case.
 */
public class LogHabitPresenter implements LogHabitOutputBoundary {

    private LogHabitViewModel logHabitViewModel;

    /**
     * Constructs a LogHabitPresenter with a given LogHabitViewModel.
     *
     * @param logHabitViewModel The view model that this presenter will interact with.
     */
    public LogHabitPresenter(LogHabitViewModel logHabitViewModel) {
        this.logHabitViewModel = logHabitViewModel;
    }
    
    /**
     * Resets the input field in the view model. This method is called as part of the output boundary
     * when the use case indicates that the input number should be reset.
     */
    @Override
    public void ResetInputNumber() {
        logHabitViewModel.resetInputField();
    }


}
