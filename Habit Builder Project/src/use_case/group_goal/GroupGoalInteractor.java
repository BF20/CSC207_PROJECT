package use_case.group_goal;

import entity.GroupGoal;

public class GroupGoalInteractor implements GroupGoalInputBoundary {
    private final GroupGoalDataAccessInterface dataAccess;
    private final GroupGoalOutputBoundary outputBoundary;

    public GroupGoalInteractor(GroupGoalDataAccessInterface dataAccess, GroupGoalOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void setGroupGoal(GroupGoalInputData inputData) {
        GroupGoal groupGoal = new GroupGoal(inputData.getGoalHours());
        dataAccess.saveGroupGoal(groupGoal);

        GroupGoalOutputData outputData = new GroupGoalOutputData(groupGoal.getGoalHours());
        outputBoundary.presentGroupGoal(outputData);
    }
}

