package app;

import entity.StudyHabitFactory;
import entity.UserFactory;
import interface_adapter.log_habit.LogHabitController;
import view.MainAppView;
import view.ViewManager;
import view.ViewManagerModel;
import data_access.FileUserDataAccessObject;
import use_case.log_habit.LogHabitInteractor;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        MainAppView mainAppView = new MainAppView();

        FileUserDataAccessObject userDataAccessObject;
        try {
            userDataAccessObject = new FileUserDataAccessObject("Habit Builder Project/data/TestData.yaml", new UserFactory(), new StudyHabitFactory());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

//        This is effectively the LogHabitUseCase Factory
        LogHabitInteractor logHabitInteractor = new LogHabitInteractor(userDataAccessObject);
        LogHabitController logHabitController = new LogHabitController(logHabitInteractor);

        // Instantiates initial users with example subjects
        mainAppView.addUserHabitLoggingPanel("Bob", logHabitController, "Math");
        mainAppView.addUserHabitLoggingPanel("User2", logHabitController, "Science");
        mainAppView.addUserHabitLoggingPanel("User3", logHabitController, "History");


        // Set up ViewManager with the card panel and layout from MainAppView
        new ViewManager(mainAppView.getCardPanel(), mainAppView.getCardLayout(), viewManagerModel);

        // Buttons to switch views
        mainAppView.addSwitchButton("Switch to Bob", () -> viewManagerModel.setActiveView("Bob"));
        mainAppView.addSwitchButton("Switch to User 2", () -> viewManagerModel.setActiveView("User2"));
        mainAppView.addSwitchButton("Switch to User 3", () -> viewManagerModel.setActiveView("User3"));

        mainAppView.display();
    }
}
