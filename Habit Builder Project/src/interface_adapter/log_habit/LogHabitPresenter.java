package interface_adapter.log_habit;

import use_case.log_habit.LogHabitOutputBoundary;
import view.LogHabit.LogHabitState;
import view.LogHabit.LogHabitViewModel;

public class LogHabitPresenter implements LogHabitOutputBoundary {

    private LogHabitViewModel logHabitViewModel;

    public LogHabitPresenter(LogHabitViewModel logHabitViewModel) {
        this.logHabitViewModel = logHabitViewModel;
    }


    @Override
    public void ResetInputNumber() {
        logHabitViewModel.resetInputField();
    }


}
