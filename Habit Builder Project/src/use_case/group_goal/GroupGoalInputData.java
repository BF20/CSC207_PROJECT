package use_case.group_goal;

public class GroupGoalInputData {
    private final int goalHours;

    public GroupGoalInputData(int goalHours) {
        this.goalHours = goalHours;
    }

    public int getGoalHours() {
        return goalHours;
    }
}
