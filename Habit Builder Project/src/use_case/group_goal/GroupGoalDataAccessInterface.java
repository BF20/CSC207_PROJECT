package use_case.group_goal;

import entity.GroupGoal;

public interface GroupGoalDataAccessInterface {
    void saveGroupGoal(GroupGoal groupGoal);
    GroupGoal getGroupGoal();
   //add more methods
}
