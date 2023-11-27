package entity;

import java.time.LocalDate;

public interface StudyHabitFactoryInterface {
    study_habit create(double time_spent, LocalDate date, String subject);
}
