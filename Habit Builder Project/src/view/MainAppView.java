package view;

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
    private JFrame frame;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JPanel buttonPanel;

    private String activeUsername;

    public MainAppView() {
        initializeComponents();
        this.activeUsername = "Bob";
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
                    String message = activeUsername + " and their friends are trying to study more. Here is a log of their studying. Write some poetic and brief words of affirmation for " + activeUsername + " based on the data in the log " + "jah";
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
