package interface_adapter.GroupGoal;

import use_case.group_goal.GroupGoalInteractor;
import use_case.group_goal.GroupGoalInputData;

public class GroupGoalController {
    private final GroupGoalInteractor groupGoalInteractor;
    private final GroupGoalViewModel groupGoalViewModel;

    public GroupGoalController(GroupGoalInteractor groupGoalInteractor, GroupGoalViewModel groupGoalViewModel) {
        this.groupGoalInteractor = groupGoalInteractor;
        this.groupGoalViewModel = groupGoalViewModel;
    }

    public void setGroupGoal(int goalHours) {
        GroupGoalInputData inputData = new GroupGoalInputData(goalHours);
        groupGoalInteractor.setGroupGoal(inputData);
    }

    public int getGroupGoal() {
        // Fetch and return the group goal from the ViewModel
        return groupGoalViewModel.getGroupGoal();
    }

    // This method can be called to update the ViewModel after setting the goal
    public void updateGroupGoalInView(int goalHours) {
        groupGoalViewModel.setGroupGoal(goalHours);
    }
}

