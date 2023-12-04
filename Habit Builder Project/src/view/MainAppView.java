package view;


import interface_adapter.GroupGoal.GroupGoalController;
import use_case.log_habit.LogHabitInteractor;

import interface_adapter.log_habit.LogHabitController;
import interface_adapter.words_of_affirmation.WOAController;
import interface_adapter.words_of_affirmation.WOAPresenter;
import use_case.Words_of_Affirmation.ChatGPTAPIExample;
import use_case.Words_of_Affirmation.StringCleaner;
import use_case.Words_of_Affirmation.chatGPT;
import view.LogHabit.LogHabitPanelView;
import view.LogHabit.LogHabitViewModel;
import view.words_of_affirmation.WOAViewModel;
import org.yaml.snakeyaml.Yaml;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class MainAppView {
    private final GroupGoalController groupGoalController;
    private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JPanel buttonPanel;


    private String activeUsername;





    public MainAppView(GroupGoalController groupGoalController) {
        // Added as a class field
        this.activeUsername = "Bob";
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

    public void addAffirmationPanel() {
        JPanel affirmationPanel = new JPanel();
        affirmationPanel.setLayout(new BoxLayout(affirmationPanel, BoxLayout.Y_AXIS));

        JButton affirmationButton = new JButton("Give Me Words of Affirmation");
        JTextField affirmationTextField = new JTextField();
        affirmationTextField.setEditable(false);



        affirmationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String toDisplay = "error";

                InputStream inputStream = null;
                try {
                    inputStream = new FileInputStream("Habit Builder Project/data/TestData.yaml");
                    Yaml yaml = new Yaml();
                    ArrayList<Map<String, Object>> data = yaml.load(inputStream);

                    // Convert the YAML data to a String
                    String yamlString = yaml.dump(data);
                    yamlString = StringCleaner.removeSpecialCharacters(yamlString);
                    String message = activeUsername + " and their friends are trying to study more. . Write some poetic, personalized and brief words of affirmation for " + activeUsername;
                    System.out.println(message);

                    toDisplay = ChatGPTAPIExample.chatGPT(message);
                    toDisplay = toDisplay.replace('\n', ' ');


                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                affirmationTextField.setText(toDisplay);

            }


        });





        affirmationPanel.add(affirmationButton);
        affirmationPanel.add(affirmationTextField);


        cardPanel.add(affirmationPanel, "AffirmationPanel");


    }



    public void addSwitchButton(String buttonText, Runnable action, String swapname) {
        JButton button = new JButton(buttonText);
        button.addActionListener(e -> action.run());
        buttonPanel.add(button);
        if (!(swapname.isEmpty())){this.activeUsername = swapname;}

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
