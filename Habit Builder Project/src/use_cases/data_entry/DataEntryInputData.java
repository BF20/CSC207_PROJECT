package use_cases.data_entry;

public class DataEntryInputData {

    final private String user;
    //input username
    final private String task;
    // the study task that they are logging, ex. leetcode, practicing math
    final private String startDay;
    // tentatively, we will have habit to be a fixed weekly habit.
    // so we will only give users the option to choose the start day, but not the end date.
    final private int amountHours;
    // tentatively, we will have hours as units


    public DataEntryInputData(String user, String task, String startDay, int hours, int amountHours) {
        this.user = user;
        this.task = task;
        this.startDay = startDay;
        this.amountHours = amountHours;
    }

    public int getAmountHours() {
        return amountHours;
    }

    public String getStartDay() {
        return startDay;
    }

    public String getTask() {
        return task;
    }

    public String getUser() {
        return user;
    }
}
