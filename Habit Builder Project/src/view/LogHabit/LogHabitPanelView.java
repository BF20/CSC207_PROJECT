package view.LogHabit;

import interface_adapter.log_habit.LogHabitController;
import use_cases.data_visualization.UserGraphPanel;
import use_cases.data_visualization.ChartRequest;
import use_cases.data_visualization.UserJsDataTry;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class LogHabitPanelView extends JPanel {

    private JTextField hoursField;
    private final LogHabitViewModel viewModel;
    private final JFrame frame;
    private final String username;
    private final String subject;
    private UserGraphPanel userGraphPanel;
    private JButton updateGraphButton;

    private final LogHabitController logHabitController;

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
                hoursField.setText("");
            }
        });
    }

    private void initializeComponents() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JLabel subjectLabel = new JLabel("Subject: " + subject);

        updateGraphButton = new JButton("Update Graph");
        updateGraphButton.addActionListener(this::updateGraph);
        add(updateGraphButton);

        hoursField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        JButton submitButton = new JButton("Log Hours");
        submitButton.addActionListener(e -> logHours(hoursField));

        add(subjectLabel);
        add(hoursField);
        add(submitButton);
    }

    private void updateGraph(ActionEvent e) {
        updateGraph();
    }

    private void updateGraph() {
        updateGraphButton.setEnabled(false);


        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    UserJsDataTry.updateUserData();


                    String timeSpentData = ChartRequest.readTimeSpentData(username);

                    List<Double> timeSpentList = ChartRequest.parseTimeSpentData(timeSpentData);

                    String jsonPart = ChartRequest.buildChartJson(timeSpentList);

                    String encodedJsonPart = ChartRequest.encodeChartJson(jsonPart);

                    String chartUrl = ChartRequest.constructChartUrl(encodedJsonPart);

                    ChartRequest.fetchAndSaveChartImage(username, chartUrl);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
//

                return null;
            }

            @Override
            protected void done() {
                updateUserGraphPanel();
                updateGraphButton.setEnabled(true);
            }
        };
        worker.execute();
    }

    private void updateUserGraphPanel() {
        if (userGraphPanel != null) {
            remove(userGraphPanel);
        }
        userGraphPanel = new UserGraphPanel(Collections.singletonList(username));
        add(userGraphPanel);
        revalidate();
        repaint();
    }

    private void logHours(JTextField hoursField) {
        try {
            double hours = Double.parseDouble(hoursField.getText());
            viewModel.setHours(hours);
            logHabitController.LogHabit(username, hours, LocalDate.now(), subject);
            viewModel.logHabit(username, subject);


            updateGraph();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid number for hours.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error logging hours: " + ex.getMessage());
        }
    }
}


//package view.LogHabit;
//
//import interface_adapter.log_habit.LogHabitController;
//import use_cases.data_visualization.UserGraphPanel;
//import use_cases.data_visualization.UserJsDataTry;
//import use_cases.data_visualization.ChartRequest;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.time.LocalDate;
//import java.util.Collections;
//import java.util.List;
//
///**
// * A panel view for logging habits. This view includes UI components
// * for inputting the number of hours spent on a habit and for triggering the log action.
// */
//public class LogHabitPanelView extends JPanel {
//
//    private JTextField hoursField;
//    private final LogHabitViewModel viewModel;
//    private final JFrame frame;
//    private final String username;
//    private final String subject;
//    private UserGraphPanel userGraphPanel;
//
//    private final LogHabitController logHabitController;
//
//    /**
//     * Constructs a new LogHabitPanelView with necessary dependencies.
//     *
//     * @param username           The username of the user logging the habit.
//     * @param subject            The subject or name of the habit.
//     * @param logHabitController The controller handling the log habit actions.
//     * @param viewModel          The view model associated with this view.
//     * @param frame              The parent frame for displaying dialogs.
//     */
//    public LogHabitPanelView(String username, String subject, LogHabitController logHabitController, LogHabitViewModel viewModel, JFrame frame) {
//        this.username = username;
//        this.subject = subject;
//        this.viewModel = viewModel;
//        this.frame = frame;
//        this.logHabitController = logHabitController;
//        this.hoursField = new JTextField(10);
//        initializeComponents();
//
//        viewModel.addPropertyChangeListener(evt -> {
//            if ("message".equals(evt.getPropertyName())) {
//                JOptionPane.showMessageDialog(frame, evt.getNewValue().toString());
//            } else if ("resetInputField".equals(evt.getPropertyName())) {
//                hoursField.setText(""); // Reset the text field when the event is fired
//            }
//        });
//    }
//
//    /**
//     * Initializes the components of this panel.
//     */
//    private void initializeComponents() {
//        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//
//        JLabel subjectLabel = new JLabel("Subject: " + subject);
//
////        Picture of graphed data goes here
//        JButton graphButton = new JButton("Update Graph");
//        graphButton.addActionListener(e -> updateGraph(e));
//        add(graphButton);
//
//        JTextField hoursField = this.hoursField;
//        Dimension maximumSize = new Dimension(Integer.MAX_VALUE, 50);
//        hoursField.setMaximumSize(maximumSize);
//
//        JButton submitButton = new JButton("Log Hours");
//        submitButton.addActionListener(e -> logHours(hoursField));
//
//        add(subjectLabel);
//        add(hoursField);
//        add(submitButton);
//    }
//
//
//
//
//    private void updateGraph(ActionEvent e) {
//        JButton updateGraphButton = (JButton) e.getSource();
//        updateGraphButton.setEnabled(false);
//
//        // Simple background task to avoid freezing the UI
//        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
//            @Override
//            protected Void doInBackground() throws Exception {
//                try {
//                    // Read the JavaScript file for time spent data
//                    String timeSpentData = ChartRequest.readTimeSpentData(username);
//
//                    // Parse the time spent data into a list of doubles
//                    List<Double> timeSpentList = ChartRequest.parseTimeSpentData(timeSpentData);
//
//                    // Build the JSON for the chart
//                    String jsonPart = ChartRequest.buildChartJson(timeSpentList);
//
//                    // Encode the JSON part for URL usage
//                    String encodedJsonPart = ChartRequest.encodeChartJson(jsonPart);
//
//                    // Construct the chart URL
//                    String chartUrl = ChartRequest.constructChartUrl(encodedJsonPart);
//
//                    // Fetch and save the chart image
//                    ChartRequest.fetchAndSaveChartImage(username, chartUrl);
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//                return null;
//            }
//
//            @Override
//            protected void done() {
//                // Handle completion of chart generation, such as updating the UI
//                updateUserGraphPanel();
//                updateGraphButton.setEnabled(true);
//            }
//        };
//        worker.execute();
//    }
//
//    private void updateUserGraphPanel() {
//        // Remove the old graph panel if present
//        if (userGraphPanel != null) {
//            remove(userGraphPanel);
//        }
//        // Create and add a new UserGraphPanel with the updated graph
//        userGraphPanel = new UserGraphPanel(Collections.singletonList(username));
//        add(userGraphPanel);
//        // Refresh the panel to display the new graph
//        revalidate();
//        repaint();
//    }
//
//
//
//    // Remove the existing graph panel
////        if (userGraphPanel != null) {
////            remove(userGraphPanel);
////        }
////
////        // Recreate the user graph panel with the username(s)
////        userGraphPanel = new UserGraphPanel(Collections.singletonList(this.username));
////
////        // Add the new graph panel
////        add(userGraphPanel);
////
////        // Refresh the panel to display the new graph
////        revalidate();
////        repaint();
//
//
//    /**
//     * Handles the logic for logging hours for a habit.
//     *
//     * @param hoursField The text field containing the number of hours to log.
//     */
//    private void logHours(JTextField hoursField) {
//        try {
//            double hours = Double.parseDouble(hoursField.getText());
//            viewModel.setHours(hours);
//            logHabitController.LogHabit(username, hours, LocalDate.now(), subject);
//
////            Put this in the presenter
////            JOptionPane.showMessageDialog(frame, "Hours logged successfully!");
//            viewModel.logHabit(username, subject);
//
//
//
//        } catch (NumberFormatException ex) {
//            JOptionPane.showMessageDialog(frame, "Please enter a valid number for hours.");
//        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(frame, "Error logging hours: " + ex.getMessage());
//        }
//    }
//}
//
//
