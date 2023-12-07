package use_case.group_goal;

import entity.GroupGoal;

/**
 * The GroupGoalInteractor class implements the business logic for managing group goals.
 * It serves as an intermediary between the data access layer and the presentation layer,
 * processing input data, performing business logic, and delegating data storage and presentation tasks.
 */
public class GroupGoalInteractor implements GroupGoalInputBoundary {
    private final GroupGoalDataAccessInterface dataAccess;
    private final GroupGoalOutputBoundary outputBoundary;

    /**
     * Constructs a GroupGoalInteractor with the specified data access and output boundary.
     *
     * @param dataAccess    The data access interface for group goal persistence.
     * @param outputBoundary The output boundary interface for presenting group goal data.
     */
    public GroupGoalInteractor(GroupGoalDataAccessInterface dataAccess, GroupGoalOutputBoundary outputBoundary) {
        this.dataAccess = dataAccess;
        this.outputBoundary = outputBoundary;
    }

    /**
     * Sets a new group goal based on the provided input data.
     * It creates a GroupGoal entity, saves it via the data access interface,
     * and then communicates the result through the output boundary.
     *
     * @param inputData The input data containing the details for the new group goal.
     */
    @Override
    public void setGroupGoal(GroupGoalInputData inputData) {
        GroupGoal groupGoal = new GroupGoal(inputData.getGoalHours());
        dataAccess.saveGroupGoal(groupGoal);

        GroupGoalOutputData outputData = new GroupGoalOutputData(groupGoal.getGoalHours());
        outputBoundary.presentGroupGoal(outputData);
    }
}
