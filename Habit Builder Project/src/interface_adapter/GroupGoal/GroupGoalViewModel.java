package interface_adapter.GroupGoal;

/**
 * The GroupGoalViewModel class represents the ViewModel in the MVVM architecture for the group goal feature.
 * It stores the state of the group goal and provides methods to access and modify this state.
 */
public class GroupGoalViewModel {
    private int groupGoal; // Represents the group study goal in hours

    /**
     * Retrieves the current group goal.
     *
     * @return The number of hours set as the current group goal.
     */
    public int getGroupGoal() {
        return groupGoal;
    }

    /**
     * Sets the group goal to the specified number of hours.
     * This method updates the state of the group goal in the ViewModel.
     *
     * @param groupGoal The number of hours to set as the new group goal.
     */
    public void setGroupGoal(int groupGoal) {
        this.groupGoal = groupGoal;
    }
}
