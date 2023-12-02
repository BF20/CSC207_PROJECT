package view;

import interface_adapter.log_habit.LogHabitController;

import java.time.LocalDate;

public class UserHabitLoggingPanelViewModel {
    private LogHabitController logHabitController;
    private String username;

    public UserHabitLoggingPanelViewModel(String username, LogHabitController logHabitController) {
        this.username = username;
        this.logHabitController = logHabitController;
    }

    public boolean logHours(double hours, String subject) {
        try {
            logHabitController.LogHabit(username, hours, LocalDate.now(), subject);
            return true; // Success
        } catch (Exception ex) {
            // Handle exception
            return false; // Failure
        }
    }
}