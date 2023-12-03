package view;

import data_access.FileUserDataAccessObject;
import entity.StudyHabitFactory;
import entity.User;
import entity.UserFactory;
import interface_adapter.log_habit.LogHabitController;
import use_cases.data_visualization.UserGraphPanel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import javax.swing.*;
import java.awt.*;
// ... (other necessary imports)
import java.util.ArrayList; // If you're using ArrayLists
import java.util.List;


public class LogHabitPanelView extends JPanel {
    private final LogHabitViewModel viewModel;
    private final JFrame frame;
    private final String username;
    private final String subject;
    private UserGraphPanel userGraphPanel;


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

        //  display the graph for the current user only
        List<String> usernames = Collections.singletonList(this.username);

        // create the user graph panel with the username
        userGraphPanel = new UserGraphPanel(usernames);
        add(userGraphPanel);

        JTextField hoursField = new JTextField(10);
        Dimension maximumSize = new Dimension(Integer.MAX_VALUE, 50);
        hoursField.setMaximumSize(maximumSize);

        JButton submitButton = new JButton("Log Hours");
        submitButton.addActionListener(e -> logHours(hoursField));


        JButton graphButton = new JButton("Update Graph");
        graphButton.addActionListener(e -> updateGraph());
        add(graphButton);

        add(subjectLabel);
        add(hoursField);
        add(submitButton);
    }

    public List<String> getUsernames() {
        String yamlPath = "Habit Builder Project/data/TestData.yaml";
        UserFactory userFactory = new UserFactory();
        StudyHabitFactory studyHabitFactory = new StudyHabitFactory();


        FileUserDataAccessObject userDataAccessObject;
        try {
            userDataAccessObject = new FileUserDataAccessObject(yamlPath, userFactory, studyHabitFactory);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


        Map<String, User> usersMap = userDataAccessObject.get_all_users();


        List<String> usernames = new ArrayList<>();


        for (String username : usersMap.keySet()) {
            usernames.add(username);
        }

        return usernames;
    }

    private void updateGraph() {
        // Remove the existing graph panel
        remove(userGraphPanel);

        // Re-fetch the usernames if they can change over time, otherwise just use the current username
        // If the usernames are fixed, you can comment out the line below
        List<String> usernames = Collections.singletonList(this.username); // Or use getUsernames() if the list can change

        // Recreate the user graph panel with the username(s)
        userGraphPanel = new UserGraphPanel(usernames);

        // Add the new graph panel
        add(userGraphPanel);

        // Refresh the panel to display the new graph
        revalidate();
        repaint();
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


