package view.LogHabit;

import interface_adapter.log_habit.LogHabitController;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

/**
 * A panel view for logging habits. This view includes UI components
 * for inputting the number of hours spent on a habit and for triggering the log action.
 */
public class LogHabitPanelView extends JPanel {

    private JTextField hoursField;
    private final LogHabitViewModel viewModel;
    private final JFrame frame;
    private final String username;
    private final String subject;

    private final LogHabitController logHabitController;

    /**
     * Constructs a new LogHabitPanelView with necessary dependencies.
     *
     * @param username           The username of the user logging the habit.
     * @param subject            The subject or name of the habit.
     * @param logHabitController The controller handling the log habit actions.
     * @param viewModel          The view model associated with this view.
     * @param frame              The parent frame for displaying dialogs.
     */
    public LogHabitPanelView(String username, String subject, LogHabitController logHabitController, LogHabitViewModel viewModel, JFrame frame) {
        this.username = username;
        this.subject = subject;
        this.viewModel = viewModel;
        this.frame = frame;
        this.logHabitController = logHabitController;
        this.hoursField = new JTextField(10);
        initializeComponents();

        viewModel.addPropertyChangeListener(evt -> {
            if ("message".equals(evt.getPropertyName())) {
                JOptionPane.showMessageDialog(frame, evt.getNewValue().toString());
            } else if ("resetInputField".equals(evt.getPropertyName())) {
                hoursField.setText(""); // Reset the text field when the event is fired
            }
        });
    }

    /**
     * Initializes the components of this panel.
     */
    private void initializeComponents() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel subjectLabel = new JLabel("Subject: " + subject);

//        Picture of graphed data goes here

        JTextField hoursField = this.hoursField;
        Dimension maximumSize = new Dimension(Integer.MAX_VALUE, 50);
        hoursField.setMaximumSize(maximumSize);

        JButton submitButton = new JButton("Log Hours");
        submitButton.addActionListener(e -> logHours(hoursField));

        add(subjectLabel);
        add(hoursField);
        add(submitButton);
    }

    /**
     * Handles the logic for logging hours for a habit.
     *
     * @param hoursField The text field containing the number of hours to log.
     */
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


