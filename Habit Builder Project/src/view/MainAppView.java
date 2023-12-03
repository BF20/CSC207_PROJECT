package view;

import interface_adapter.GroupGoal.GroupGoalController;
import use_case.log_habit.LogHabitInteractor;
import interface_adapter.log_habit.LogHabitController;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class MainAppView {
    private final GroupGoalController groupGoalController;
    private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JPanel buttonPanel;


    public MainAppView(GroupGoalController groupGoalController) {
        // Added as a class field
        this.groupGoalController = groupGoalController;
        initializeComponents();
        addGroupGoalView(groupGoalController);
        addGroupGoalButton();
    }

    //    All the initialization stuff from the main file in the CA Engine example
    private void initializeComponents() {
        frame = new JFrame("User Screen Switcher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);


        // Example user screens (panels)
//        cardPanel.add(createUserPanel("User 1 Screen"), "User1");
//        cardPanel.add(createUserPanel("User 2 Screen"), "User2");
//        cardPanel.add(createUserPanel("User 3 Screen"), "User3");


        buttonPanel = new JPanel();
        frame.add(cardPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void addUserHabitLoggingPanel(String username, LogHabitController logHabitController, String subject) {
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));

        JLabel subjectLabel = new JLabel("Subject: " + subject);
        JTextField hoursField = new JTextField(10);
        Dimension maximumSize = new Dimension(Integer.MAX_VALUE, 50);
        hoursField.setMaximumSize(maximumSize);

        JButton submitButton = new JButton("Log Hours");

        submitButton.addActionListener(e -> {
            try {
                double hours = Double.parseDouble(hoursField.getText());

                logHabitController.LogHabit(username, hours, LocalDate.now(), subject);
                JOptionPane.showMessageDialog(frame, "Hours logged successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid number for hours.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Error logging hours: " + ex.getMessage());
            }
        });

        userPanel.add(subjectLabel);
        userPanel.add(hoursField);
        userPanel.add(submitButton);
        cardPanel.add(userPanel, username);
    }


    public void display() {
        frame.add(cardPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    public void addGroupGoalView(GroupGoalController groupGoalController) {
        GroupGoalView groupGoalView = new GroupGoalView(groupGoalController);
        cardPanel.add(groupGoalView, "GroupGoal");
    }

    public void addGroupGoalButton() {
        JButton groupGoalButton = new JButton("Set Group Goal");
        groupGoalButton.addActionListener(e -> cardLayout.show(cardPanel, "GroupGoal"));
        buttonPanel.add(groupGoalButton);
    }

    private JPanel createUserPanel(String text) {
        JPanel panel = new JPanel();
        panel.add(new JLabel(text));
        return panel;
    }

    public void addSwitchButton(String buttonText, String cardName) {
        JButton button = new JButton(buttonText);
        button.addActionListener(e -> cardLayout.show(cardPanel, cardName));
        buttonPanel.add(button);
    }


    public JPanel getCardPanel() {
        return cardPanel;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }
}
