package interface_adapter.GroupGoal;

import use_case.group_goal.GroupGoalOutputBoundary;
import use_case.group_goal.GroupGoalOutputData;

public class GroupGoalPresenter implements GroupGoalOutputBoundary {
    private final GroupGoalViewModel groupGoalViewModel;

    public GroupGoalPresenter(GroupGoalViewModel groupGoalViewModel) {
        this.groupGoalViewModel = groupGoalViewModel;
    }

    @Override
    public void presentGroupGoal(GroupGoalOutputData outputData) {
        groupGoalViewModel.setGroupGoal(outputData.getGoalHours());
    }
}

