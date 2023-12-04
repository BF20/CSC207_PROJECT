package view;


import interface_adapter.GroupGoal.GroupGoalController;
import use_case.log_habit.LogHabitInteractor;

import interface_adapter.log_habit.LogHabitController;
import view.LogHabit.LogHabitPanelView;
import view.LogHabit.LogHabitViewModel;

import javax.swing.*;
import java.awt.*;

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
        frame = new JFrame("PROGRESS PAL");
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

    public void addUserHabitLoggingPanel(String username, LogHabitController logHabitController, String subject, LogHabitViewModel viewModel) {
        // Create an instance of the ViewModel

        // Create the user habit logging panel view with the ViewModel
        LogHabitPanelView userPanel = new LogHabitPanelView(username, subject, logHabitController, viewModel, frame);

        // Add the panel to your card layout
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
