package view;

import interface_adapter.log_habit.LogHabitController;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class UserHabitLoggingPanelView extends JPanel {
    private final LogHabitController logHabitController;
    private final String username;
    private final JFrame frame;

    private final String subject;

    public UserHabitLoggingPanelView(String username, LogHabitController logHabitController, String subject, JFrame frame) {
        this.logHabitController = logHabitController;
        this.username = username;
        this.frame = frame;
        this.subject = subject;
        initializeComponents(subject);

    }

    private void initializeComponents(String subject) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel subjectLabel = new JLabel("Subject: " + subject);
        JTextField hoursField = new JTextField(10);
        Dimension maximumSize = new Dimension(Integer.MAX_VALUE, 50);
        hoursField.setMaximumSize(maximumSize);

        JButton submitButton = new JButton("Log Hours");
        submitButton.addActionListener(e -> logHours(hoursField));

        add(subjectLabel);
        add(hoursField);
        add(submitButton);
    }

    private void logHours(JTextField hoursField) {
        try {
            double hours = Double.parseDouble(hoursField.getText());
            logHabitController.LogHabit(username, hours, LocalDate.now(), subject);
            JOptionPane.showMessageDialog(frame, "Hours logged successfully!");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid number for hours.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error logging hours: " + ex.getMessage());
        }
    }
}

