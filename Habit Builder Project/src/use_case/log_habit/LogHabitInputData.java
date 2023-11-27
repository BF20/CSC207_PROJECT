package use_case.log_habit;

import java.time.LocalDate;

public class LogHabitInputData {
    private final String username;
    private final double hours;
    private final LocalDate date;
    private final String subject;

    public LogHabitInputData(String username, double hours, LocalDate date, String subject) {
        this.username = username;
        this.hours = hours;
        this.date = date;
        this.subject = subject;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public double getHours() {
        return hours;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getSubject() {
        return subject;
    }
}
