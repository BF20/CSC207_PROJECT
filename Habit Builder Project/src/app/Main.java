package app;

import view.ViewManager;
import view.ViewManagerModel;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {

        JFrame application = new JFrame("User Screen Switch 1.0");
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.setSize(400, 300);


        CardLayout cardLayout = new CardLayout();
        JPanel views = new JPanel(cardLayout);

        application.add(views, BorderLayout.CENTER);

        // Example user screens (panels)
        JPanel user1Panel = createUserPanel("User 1 Screen");
        JPanel user2Panel = createUserPanel("User 2 Screen");
        JPanel user3Panel = createUserPanel("User 3 Screen");

        views.add(user1Panel, "User1");
        views.add(user2Panel, "User2");
        views.add(user3Panel, "User3");

        // ViewManager setup
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        // Buttons to switch views. Irrespective of view model right now
        JPanel buttonPanel = new JPanel();
        addButton(buttonPanel, "Switch to User 1", () -> viewManagerModel.setActiveView("User1"));
        addButton(buttonPanel, "Switch to User 2", () -> viewManagerModel.setActiveView("User2"));
        addButton(buttonPanel, "Switch to User 3", () -> viewManagerModel.setActiveView("User3"));


        application.add(buttonPanel, BorderLayout.SOUTH);

        application.setVisible(true);
    }

    private static JPanel createUserPanel(String text) {
        JPanel panel = new JPanel();
        panel.add(new JLabel(text));
        return panel;
    }

    private static void addButton(JPanel panel, String text, Runnable action) {
        JButton button = new JButton(text);
        button.addActionListener(e -> action.run());
        panel.add(button);
    }
}



