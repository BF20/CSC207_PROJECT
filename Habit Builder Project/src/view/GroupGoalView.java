package view;
import interface_adapter.GroupGoal.GroupGoalController;

import javax.swing.*;
import java.awt.*;

public class GroupGoalView extends JPanel {
    private final GroupGoalController groupGoalController;
    private JTextField goalField;
    private JLabel currentGoalLabel;

    public GroupGoalView(GroupGoalController groupGoalController) {
        this.groupGoalController = groupGoalController;
        initializeComponents();
    }

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


    public void updateCurrentGoalLabel() {
        // Fetch the current goal from the controller, which in turn gets it from the ViewModel
        int currentGoal = groupGoalController.getGroupGoal();
        currentGoalLabel.setText("Current Group Goal: " + currentGoal);
    }
}


