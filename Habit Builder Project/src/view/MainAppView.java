package view;

import use_case.log_habit.LogHabitInteractor;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

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
        cardPanel.add(createUserPanel("User 1 Screen"), "User1");
        cardPanel.add(createUserPanel("User 2 Screen"), "User2");
        cardPanel.add(createUserPanel("User 3 Screen"), "User3");
        

        buttonPanel = new JPanel();
    }

    public void addUserHabitLoggingPanel(String username, LogHabitInteractor logHabitInteractor, String subject) {
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));

        JLabel subjectLabel = new JLabel("Subject: " + subject);
        JTextField hoursField = new JTextField(10);
        JButton submitButton = new JButton("Log Hours");

        submitButton.addActionListener(e -> {
            try {
                double hours = Double.parseDouble(hoursField.getText());
                logHabitInteractor.LogHabit(username, hours, LocalDate.now(), subject);
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

    private JPanel createUserPanel(String text) {
        JPanel panel = new JPanel();
        panel.add(new JLabel(text));
        return panel;
    }

    public JPanel getCardPanel() {
        return cardPanel;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }
}
