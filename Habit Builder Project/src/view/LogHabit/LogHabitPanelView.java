package view.LogHabit;

import interface_adapter.log_habit.LogHabitController;
import use_cases.data_visualization.ImageResizer;
import use_cases.data_visualization.UserGraphPanel;
import use_cases.data_visualization.ChartRequest;
import use_cases.data_visualization.UserJsDataTryInteractor;

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
            private String resizedImagePath;
            @Override
            protected Void doInBackground() throws Exception {
                try {
                    UserJsDataTryInteractor.updateUserData();


                    String timeSpentData = ChartRequest.readTimeSpentData(username);

                    List<Double> timeSpentList = ChartRequest.parseTimeSpentData(timeSpentData);

                    String jsonPart = ChartRequest.buildChartJson(timeSpentList);

                    String encodedJsonPart = ChartRequest.encodeChartJson(jsonPart);

                    String chartUrl = ChartRequest.constructChartUrl(encodedJsonPart);
                   // String chartUrl = ChartRequest.constructChartUrl(encodedJsonPart);
                    String imagePath = "Habit Builder Project/src/use_cases/data_visualization/graphs/" + username +"_chart.png"; // The path where the image is saved after fetching
                    ChartRequest.fetchAndSaveChartImage(username, chartUrl);

                     //Resize the image
                    String resizedImagePath = "Habit Builder Project/src/use_cases/data_visualization/graphs/" ; // Path to save the resized image
                    //resizedImagePath = "path/to/resized/image.png"; // Replace with actual path
                    ImageResizer.resize(imagePath, resizedImagePath, 150, 75); // Resize image

                    ChartRequest.fetchAndSaveChartImage(username, chartUrl);


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
//

                return null;
            }

            @Override
            protected void done() {

                updateUserGraphPanel(resizedImagePath);
                updateGraphButton.setEnabled(true);
            }
        };
        worker.execute();
    }

    private void updateUserGraphPanel(String resizedImagePath) {
        if (userGraphPanel != null) {
            remove(userGraphPanel);
        }
        userGraphPanel = new UserGraphPanel(Collections.singletonList(username));
        userGraphPanel.updateGraphImage(  "Habit Builder Project/src/use_cases/data_visualization/graphs/"+ username + "_chart.png");
        add(userGraphPanel);
        revalidate();
        repaint();
//        if (userGraphPanel != null) {
//            remove(userGraphPanel);
//        }
//        userGraphPanel = new UserGraphPanel(Collections.singletonList(username));
////        userGraphPanel.setPreferredSize(new Dimension(300, 150));
//        add(userGraphPanel);
//        revalidate();
//        repaint();
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


