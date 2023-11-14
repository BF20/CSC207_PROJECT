package app;

import view.ViewManager;
import view.ViewManagerModel;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
     
        JFrame frame = new JFrame("User Screen Switch 1.0");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        ViewManagerModel viewManagerModel = new ViewManagerModel();

        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);

        // Example user screens (panels)
        JPanel user1Panel = createUserPanel("User 1 Screen");
        JPanel user2Panel = createUserPanel("User 2 Screen");
        JPanel user3Panel = createUserPanel("User 3 Screen");

        cardPanel.add(user1Panel, "User1");
        cardPanel.add(user2Panel, "User2");
        cardPanel.add(user3Panel, "User3");

        // ViewManager setup
        new ViewManager(cardPanel, cardLayout, viewManagerModel);

        // Buttons to switch views
        JPanel buttonPanel = new JPanel();
        addButton(buttonPanel, "Switch to User 1", () -> viewManagerModel.setActiveView("User1"));
        addButton(buttonPanel, "Switch to User 2", () -> viewManagerModel.setActiveView("User2"));
        addButton(buttonPanel, "Switch to User 3", () -> viewManagerModel.setActiveView("User3"));

        frame.add(cardPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
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



