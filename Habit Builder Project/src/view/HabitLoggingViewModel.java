package view;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class HabitLoggingViewModel {
    private double hours;
    private String message;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public HabitLoggingViewModel() {
    }

    public double getHours() {
        return hours;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        String oldMessage = this.message;
        this.message = message;
        support.firePropertyChange("message", oldMessage, this.message);
    }

    public void logHabit(String username, String subject) {
        // Implement the logic to log the habit
        // For example:
        try {
            // Assuming a method in your controller: logHabitController.logHabit(username, hours, LocalDate.now(), subject);
            // Update message accordingly
            setMessage("Hours logged successfully!");
        } catch (Exception ex) {
            setMessage("Error logging hours: " + ex.getMessage());
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}


