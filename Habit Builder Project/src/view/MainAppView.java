package view;

import interface_adapter.log_habit.LogHabitController;
import interface_adapter.log_habit.LogHabitPanelView;
import interface_adapter.log_habit.LogHabitViewModel;

import javax.swing.*;
import java.awt.*;

public class MainAppView {
    private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JPanel buttonPanel;

    public MainAppView() {
        initializeComponents();
    }

    //    All the initialization stuff from the main file in the CA Engine example
    private void initializeComponents() {
        frame = new JFrame("User Screen Switcher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Example user screens (panels)
//        cardPanel.add(createUserPanel("User 1 Screen"), "User1");
//        cardPanel.add(createUserPanel("User 2 Screen"), "User2");
//        cardPanel.add(createUserPanel("User 3 Screen"), "User3");


        buttonPanel = new JPanel();
    }

    public void addUserHabitLoggingPanel(String username, LogHabitController logHabitController, String subject) {
        // Create an instance of the ViewModel
        LogHabitViewModel viewModel = new LogHabitViewModel();

        // Create the user habit logging panel view with the ViewModel
        LogHabitPanelView userPanel = new LogHabitPanelView(username, subject, logHabitController, viewModel, frame);

        // Add the panel to your card layout
        cardPanel.add(userPanel, username);
    }

    public void addSwitchButton(String buttonText, Runnable action) {
        JButton button = new JButton(buttonText);
        button.addActionListener(e -> action.run());
        buttonPanel.add(button);
    }

    public void display() {
        frame.add(cardPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

//    private JPanel createUserPanel(String text) {
//        JPanel panel = new JPanel();
//        panel.add(new JLabel(text));
//        return panel;
//    }

    public JPanel getCardPanel() {
        return cardPanel;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }
}
