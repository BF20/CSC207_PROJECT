package interface_adapter.GroupGoal;

import use_case.group_goal.GroupGoalInputBoundary;
import use_case.group_goal.GroupGoalInputData;

/**
 * The GroupGoalController class is responsible for handling user input related to group goals,
 * delegating business logic to the GroupGoalInteractor, and updating the GroupGoalViewModel.
 */
public class GroupGoalController {
    private final GroupGoalInputBoundary groupGoalInteractor;
    private final GroupGoalViewModel groupGoalViewModel;

    /**
     * Constructs a GroupGoalController with the specified interactor and ViewModel.
     *
     * @param groupGoalInteractor The interactor responsible for group goal business logic.
     * @param groupGoalViewModel  The ViewModel storing the state of the group goal.
     */
    public GroupGoalController(GroupGoalInputBoundary groupGoalInteractor, GroupGoalViewModel groupGoalViewModel) {
        this.groupGoalInteractor = groupGoalInteractor;
        this.groupGoalViewModel = groupGoalViewModel;
    }

    /**
     * Sets the group goal in hours.
     * Delegates the setting of the group goal to the GroupGoalInteractor.
     *
     * @param goalHours The number of hours for the group goal.
     */
    public void setGroupGoal(int goalHours) {
        GroupGoalInputData inputData = new GroupGoalInputData(goalHours);
        groupGoalInteractor.setGroupGoal(inputData);
    }

    /**
     * Retrieves the current group goal from the ViewModel.
     *
     * @return The current number of hours set as the group goal.
     */
    public int getGroupGoal() {
        return groupGoalViewModel.getGroupGoal();
    }

    /**
     * Updates the group goal in the ViewModel.
     * This method is typically used to reflect changes in the view.
     *
     * @param goalHours The updated number of hours for the group goal.
     */
    public void updateGroupGoalInView(int goalHours) {
        groupGoalViewModel.setGroupGoal(goalHours);
    }
}
