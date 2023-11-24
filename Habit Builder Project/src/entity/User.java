package entity;
import java.util.ArrayList;
import java.util.Map;
import java.time.LocalDate;

public class User {
    private final String username;

    private boolean admin;

    private final ArrayList<Habit>  completed_habits;


    User(String username, boolean admin, ArrayList<Habit> completed_habits) {
        this.username = username;
        this.admin = admin;
        this.completed_habits =completed_habits;
    }


    public void AddHabit(Habit habit) {
        this.completed_habits.add(habit);
    }



    public ArrayList<Habit> GetAllCompletedHabits() {
        return this.completed_habits;

    }



    public boolean GetAdminStatus() {
        return this.admin;
    }

    public void SetAdminStatus(boolean status) {
        this.admin = status;
    }

    public String GetUsername() {
        return this.username;
    }

    // Constructor


}
