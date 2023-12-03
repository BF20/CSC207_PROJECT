package app;

import entity.StudyHabitFactory;
import entity.UserFactory;
import interface_adapter.log_habit.LogHabitController;
import interface_adapter.log_habit.SwitchScreenController;
import use_case.switch_screens.SwitchScreenInteractor;
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

        // Make the SwitchScreen Controller
        SwitchScreenInteractor switchScreenInteractor = new SwitchScreenInteractor(viewManagerModel);
        SwitchScreenController switchScreenController = new SwitchScreenController(switchScreenInteractor);

        // Instantiates initial users with example subjects
        mainAppView.addUserHabitLoggingPanel("Bob", logHabitController, "None");
        mainAppView.addUserHabitLoggingPanel("Alice", logHabitController, "None");
        mainAppView.addUserHabitLoggingPanel("Charlie", logHabitController, "None");


        // Set up ViewManager with the card panel and layout from MainAppView
        new ViewManager(mainAppView.getCardPanel(), mainAppView.getCardLayout(), viewManagerModel);

        // Buttons to switch views
        // Take out second argument and plug in the controller
        mainAppView.addSwitchButton("Switch to Bob", () -> switchScreenController.switchToUserScreen("Bob"));
        mainAppView.addSwitchButton("Switch to Alice", () -> switchScreenController.switchToUserScreen("Alice"));
        mainAppView.addSwitchButton("Switch to Charlie", () -> switchScreenController.switchToUserScreen("Charlie"));

        mainAppView.display();
    }
}
