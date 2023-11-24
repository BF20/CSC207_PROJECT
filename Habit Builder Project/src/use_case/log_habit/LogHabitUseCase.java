package use_case.log_habit;

import entity.Habit;

import java.time.LocalDate;

public interface LogHabitUseCase {
    void LogHabit(String username, double hours, LocalDate date, String subject) throws Exception;
}

