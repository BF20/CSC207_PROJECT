package use_case.log_habit;

import java.time.LocalDate;

public interface LogHabitInputBoundary {
    void LogHabit(LogHabitInputData inputData) throws Exception;
}

