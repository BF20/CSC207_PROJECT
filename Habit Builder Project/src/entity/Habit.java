package entity;

import java.time.LocalDate;
import java.util.ArrayList;


public class Habit {

    public String habit_name;

    public String emoji;

    int time_spent;

    LocalDate day;

    ArrayList<Session> session;
}

class StudyHabit extends Habit {

}

class ExerciseHabit extends Habit {

}

class HobbyHabit extends Habit {

}

//Study
//Exercise
//Hobby



