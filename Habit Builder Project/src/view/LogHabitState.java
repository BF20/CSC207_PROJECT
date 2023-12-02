package view;

/**
 * Represents the state of a habit logging action.
 * It holds the number of hours spent on a habit and a message related to the habit logging.
 */
public class LogHabitState {
    private double hours;
    private String message;

    /**
     * Default constructor initializing the state with default values.
     */
    public LogHabitState() {
        this.hours = 0.0;
        this.message = "";
    }

    /**
     * Gets the number of hours spent on the habit.
     *
     * @return The hours spent.
     */
    public double getHours() {
        return hours;
    }

    /**
     * Sets the number of hours spent on the habit.
     *
     * @param hours The hours to set.
     */
    public void setHours(double hours) {
        this.hours = hours;
    }

    /**
     * Gets the message related to habit logging.
     *
     * @return The message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message related to habit logging.
     *
     * @param message The message to set.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
