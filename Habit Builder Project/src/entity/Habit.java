package entity;

import java.time.LocalDate;


public abstract class Habit {

    public String habit_name;

    public String emoji;

    public int time_spent;

    public LocalDate creation_date;

    public LocalDate end_date;



}

class study_habit extends Habit {

}

class exercise_habit extends Habit {

}

class hobby_habit extends Habit {

}





