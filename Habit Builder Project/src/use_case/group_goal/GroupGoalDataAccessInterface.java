package use_case.group_goal;

import entity.GroupGoal;

/**
 * The GroupGoalDataAccessInterface defines the contract for data access operations related to GroupGoals.
 * Implementations of this interface provide the means to persist and retrieve group goal data, typically from a data store.
 */
public interface GroupGoalDataAccessInterface {

    /**
     * Saves the specified GroupGoal object to the data store.
     * This method should handle the persistence logic for a group goal, such as saving it to a database or a file.
     *
     * @param groupGoal The GroupGoal object to be saved.
     */
    void saveGroupGoal(GroupGoal groupGoal);

    /**
     * Retrieves the currently stored GroupGoal.
     * This method should handle the retrieval logic of the group goal from the data store.
     *
     * @return The currently stored GroupGoal object.
     */
    GroupGoal getGroupGoal();

    // Placeholder for additional methods
    // add more methods
}

