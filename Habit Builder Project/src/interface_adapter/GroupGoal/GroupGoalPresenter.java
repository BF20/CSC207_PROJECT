package interface_adapter.GroupGoal;

import use_case.group_goal.GroupGoalOutputBoundary;
import use_case.group_goal.GroupGoalOutputData;

/**
 * The GroupGoalPresenter class is responsible for handling the presentation logic
 * of the group goal data. It acts as a bridge between the use case (business logic)
 * and the ViewModel, updating the ViewModel based on the data received from the use case.
 */
public class GroupGoalPresenter implements GroupGoalOutputBoundary {
    private final GroupGoalViewModel groupGoalViewModel;

    /**
     * Constructs a GroupGoalPresenter with the specified ViewModel.
     *
     * @param groupGoalViewModel The ViewModel that will be updated with group goal data.
     */
    public GroupGoalPresenter(GroupGoalViewModel groupGoalViewModel) {
        this.groupGoalViewModel = groupGoalViewModel;
    }

    /**
     * Presents the group goal by updating the ViewModel.
     * This method is called by the interactor to present the result of business logic execution.
     *
     * @param outputData The data output by the interactor, containing the group goal information.
     */
    @Override
    public void presentGroupGoal(GroupGoalOutputData outputData) {
        groupGoalViewModel.setGroupGoal(outputData.getGoalHours());
    }
}
