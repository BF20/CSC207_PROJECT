package entity;

import java.time.LocalDate;

public class StudyHabitFactory  implements StudyHabitFactoryInterface {


    @Override
    public study_habit create(double time_spent, LocalDate date, String subject) {
        return new study_habit(time_spent, date, subject);
    }
}
