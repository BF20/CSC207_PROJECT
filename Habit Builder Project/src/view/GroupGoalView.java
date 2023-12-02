package view;

import use_case.log_habit.LogHabitInteractor;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class GroupGoalView {
    private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JPanel buttonPanel;

    public GroupGoalView () {
        initializeComponents();
    }

    private void initializeComponents() {
        frame = new JFrame("User Screen Switcher");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        buttonPanel = new JPanel();
    }
    public void addUserHabitLoggingPanel(String username, LogHabitInteractor logHabitInteractor, String subject) {
        addGroupGoal(username, logHabitInteractor, subject, frame, cardPanel);
    }

    static void addGroupGoal(String username, LogHabitInteractor logHabitInteractor, String subject, JFrame frame, JPanel cardPanel) {
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


