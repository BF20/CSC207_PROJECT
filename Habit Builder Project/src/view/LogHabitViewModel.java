package view;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * The view model for LogHabit use casel. It handles the data-related logic for the view
 * and communicates changes in the state to the view.
 */
public class LogHabitViewModel {
    private LogHabitState state;
    private final PropertyChangeSupport support;

    /**
     * Constructor initializing the view model with a new state and property change support.
     */
    public LogHabitViewModel() {
        this.state = new LogHabitState();
        this.support = new PropertyChangeSupport(this);
    }

    /**
     * Handles the logic for logging a habit in the view model, updating the state message accordingly.
     *
     * @param username The username of the user logging the habit.
     * @param subject  The subject or name of the habit.
     */

    public void logHabit(String username, String subject) {

        try {

            updateStateMessage("Hours logged successfully!");
        } catch (Exception ex) {
            updateStateMessage("Error logging hours: " + ex.getMessage());
        }
    }

    /**
     * Sets the number of hours in the current state.
     *
     * @param hours The number of hours to set.
     */
    public void setHours(double hours) {
        state.setHours(hours);
    }

    /**
     * Updates the state message and notifies listeners about the change.
     *
     * @param message The new message to set in the state.
     */
    public void updateStateMessage(String message) {
        String oldMessage = state.getMessage();
        state.setMessage(message);
        support.firePropertyChange("message", oldMessage, message);
    }

    /**
     * Adds a property change listener to this view model.
     *
     * @param listener The property change listener to add.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Optionally exposes the state to the view.
     *
     * @return The current state.
     */
    // Optional: If you need to expose state to the view
    public LogHabitState getState() {
        return state;
    }
}



