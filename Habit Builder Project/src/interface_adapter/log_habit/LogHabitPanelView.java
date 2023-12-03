package interface_adapter.log_habit;

import interface_adapter.log_habit.LogHabitController;
import view.LogHabitViewModel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class LogHabitPanelView extends JPanel {
    private final LogHabitViewModel viewModel;
    private final JFrame frame;
    private final String username;
    private final String subject;

    private final LogHabitController logHabitController;

    public LogHabitPanelView(String username, String subject, LogHabitController logHabitController, LogHabitViewModel viewModel, JFrame frame) {
        this.username = username;
        this.subject = subject;
        this.viewModel = viewModel;
        this.frame = frame;
        this.logHabitController = logHabitController;
        initializeComponents();

        viewModel.addPropertyChangeListener(evt -> {
            if ("message".equals(evt.getPropertyName())) {
                JOptionPane.showMessageDialog(frame, evt.getNewValue().toString());
            }
        });
    }

    private void initializeComponents() {
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
            viewModel.setHours(hours);
            logHabitController.LogHabit(username, hours, LocalDate.now(), subject);

//            Put this in the presenter
//            JOptionPane.showMessageDialog(frame, "Hours logged successfully!");
            viewModel.logHabit(username, subject);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid number for hours.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error logging hours: " + ex.getMessage());
        }
    }
}


