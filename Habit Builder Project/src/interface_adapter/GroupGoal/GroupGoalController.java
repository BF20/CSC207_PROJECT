package interface_adapter.GroupGoal;

import use_case.group_goal.GroupGoalInputBoundary;
import use_case.group_goal.GroupGoalInteractor;
import use_case.group_goal.GroupGoalInputData;

public class GroupGoalController {
    private final GroupGoalInputBoundary groupGoalInteractor;
    private final GroupGoalViewModel groupGoalViewModel;

    public GroupGoalController(GroupGoalInputBoundary groupGoalInteractor, GroupGoalViewModel groupGoalViewModel) {
        this.groupGoalInteractor = groupGoalInteractor;
        this.groupGoalViewModel = groupGoalViewModel;
    }


    public void setGroupGoal(int goalHours) {
        GroupGoalInputData inputData = new GroupGoalInputData(goalHours);
        groupGoalInteractor.setGroupGoal(inputData);
    }

    public int getGroupGoal() {

        return groupGoalViewModel.getGroupGoal();
    }

    public void updateGroupGoalInView(int goalHours) {
        groupGoalViewModel.setGroupGoal(goalHours);
    }
}

