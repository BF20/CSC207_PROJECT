package entity;
import java.util.ArrayList;
import java.util.Map;
import java.time.LocalDate;

public class User {
    private String username;

    private boolean admin;

    private ArrayList<Habit>  completed_habits;

    private Map<String, Habit> active_habits;


    public void AddHabit(Habit habit) {
        this.active_habits.put(habit.habit_name, habit);
    }

    public void updateState() {
        for (Habit habit : this.active_habits.values()) {
            if (LocalDate.now().isAfter(habit.end_date)) {
                // Add the habit to "completed_habits"
                this.completed_habits.add(habit);
                // remove the habit from "active_habits"
                this.active_habits.remove(habit.habit_name);
            }

        }


    }

    public ArrayList<Habit> GetAllCompletedHabits() {
        return this.completed_habits;

    }

    public Habit GetActiveHabit(String habit_name) {
        return this.active_habits.get(habit_name);
    }

    public Map<String, Habit> GetAllActiveHabits() {
        return this.active_habits;
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
