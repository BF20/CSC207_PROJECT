package view;

import interface_adapter.GroupGoal.GroupGoalController;

import javax.swing.*;
import java.awt.*;

/**
 * The GroupGoalView class represents the view component for setting and displaying group goals.
 * It allows users to input a group study goal in hours and displays the current group goal.
 */
public class GroupGoalView extends JPanel {
    private final GroupGoalController groupGoalController;
    private JTextField goalField;
    private JLabel currentGoalLabel;

    /**
     * Constructs a GroupGoalView with the specified GroupGoalController.
     *
     * @param groupGoalController The controller that manages group goal interactions.
     */
    public GroupGoalView(GroupGoalController groupGoalController) {
        this.groupGoalController = groupGoalController;
        initializeComponents();
    }

    /**
     * Initializes the components of the GroupGoalView.
     * Sets up the input field, button, and label for displaying and setting the group goal.
     */
    private void initializeComponents() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Input field for setting the goal
        goalField = new JTextField(10);
        goalField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        // Button to set the goal
        JButton setGoalButton = new JButton("Set Goal");
        setGoalButton.addActionListener(e -> setGroupGoal());

        // Label to display the current goal
        currentGoalLabel = new JLabel("Current Group Goal: ");

        this.add(new JLabel("Set Group Study Goal (in hours):"));
        this.add(goalField);
        this.add(setGoalButton);
        this.add(currentGoalLabel);
    }

    /**
     * Sets the group goal based on the input from the user.
     * Notifies the user if the input is invalid or if the goal is set successfully.
     */
    private void setGroupGoal() {
        try {
            int goalHours = Integer.parseInt(goalField.getText());
            groupGoalController.setGroupGoal(goalHours);
            updateCurrentGoalLabel();
            JOptionPane.showMessageDialog(this, "Group goal set successfully!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for hours.");
        }
    }

    /**
     * Updates the label displaying the current group goal.
     * Fetches the current goal from the controller, which in turn gets it from the ViewModel.
     */
    public void updateCurrentGoalLabel() {
        int currentGoal = groupGoalController.getGroupGoal();
        currentGoalLabel.setText("Current Group Goal: " + currentGoal);
    }
}
