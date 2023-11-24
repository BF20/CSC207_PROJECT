package entity;

import java.time.LocalDate;

public class study_habit extends Habit {

    public String subject;


    public study_habit(double time_spent, LocalDate date, String subject) {
        this.time_spent = time_spent;
        this.date = date;
        this.subject = subject;


    }

}
