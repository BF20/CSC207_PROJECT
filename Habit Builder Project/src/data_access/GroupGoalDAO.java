package data_access;

import entity.GroupGoal;
import use_case.group_goal.GroupGoalDataAccessInterface;

public class GroupGoalDAO implements GroupGoalDataAccessInterface {
    private GroupGoal currentGroupGoal;

    @Override
    public void saveGroupGoal(GroupGoal groupGoal) {
        this.currentGroupGoal = groupGoal;
    }

    @Override
    public GroupGoal getGroupGoal() {
        return currentGroupGoal;
    }
}

