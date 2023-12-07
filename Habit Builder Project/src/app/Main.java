package app;

import data_access.GroupGoalDAO;
import entity.StudyHabitFactory;
import entity.UserFactory;
import interface_adapter.GroupGoal.GroupGoalController;
import interface_adapter.GroupGoal.GroupGoalPresenter;
import interface_adapter.GroupGoal.GroupGoalViewModel;
import interface_adapter.log_habit.LogHabitController;


import use_case.group_goal.GroupGoalDataAccessInterface;
import use_case.group_goal.GroupGoalInteractor;
import use_case.group_goal.GroupGoalOutputBoundary;

import view.LogHabit.LogHabitViewModel;


import interface_adapter.log_habit.SwitchScreenController;
import use_case.switch_screens.SwitchScreenInteractor;

import view.MainAppView;
import view.ViewManager;
import view.ViewManagerModel;
import data_access.FileUserDataAccessObject;
import use_case.log_habit.LogHabitInteractor;
import interface_adapter.log_habit.LogHabitPresenter;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        ViewManagerModel viewManagerModel = new ViewManagerModel();

        FileUserDataAccessObject userDataAccessObject;
        try {
            userDataAccessObject = new FileUserDataAccessObject("Habit Builder Project/data/TestData.yaml", new UserFactory(), new StudyHabitFactory());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        GroupGoalDataAccessInterface dataAccess = new GroupGoalDAO();

        // Instantiate the ViewModel
        GroupGoalViewModel viewModel = new GroupGoalViewModel();


        // Instantiate the Presenter
        GroupGoalOutputBoundary presenter = new GroupGoalPresenter(viewModel);

        // Instantiate the Interactor with its concrete type
        GroupGoalInteractor interactor = new GroupGoalInteractor(dataAccess, presenter);

        // Instantiate the Controller with the interactor as GroupGoalInputBoundary
        GroupGoalController groupGoalController = new GroupGoalController(interactor, viewModel);

        // Set up the MainAppView and add the GroupGoalPanel
        MainAppView mainAppView = new MainAppView(groupGoalController);

        // Then add the GroupGoalView and its button
        mainAppView.addGroupGoalView(groupGoalController);

        // This is effectively the LogHabitUseCase Factory
        LogHabitViewModel logHabitViewModel = new LogHabitViewModel();
        LogHabitPresenter logHabitPresenter = new LogHabitPresenter(logHabitViewModel);
        LogHabitInteractor logHabitInteractor = new LogHabitInteractor(userDataAccessObject, logHabitPresenter);
        LogHabitController logHabitController = new LogHabitController(logHabitInteractor);


        // Make the SwitchScreen Controller
        SwitchScreenInteractor switchScreenInteractor = new SwitchScreenInteractor(viewManagerModel);
        SwitchScreenController switchScreenController = new SwitchScreenController(switchScreenInteractor);

        // Instantiates initial users with example subjects
        mainAppView.addUserHabitLoggingPanel("Bob", logHabitController, "None", logHabitViewModel);
        mainAppView.addUserHabitLoggingPanel("Alice", logHabitController, "None", logHabitViewModel);
        mainAppView.addUserHabitLoggingPanel("Charlie", logHabitController, "None", logHabitViewModel);


        // Buttons to switch views
        // Take out second argument and plug in the controller
        mainAppView.addSwitchButton("Switch to Bob", () -> switchScreenController.switchToUserScreen("Bob"));
        mainAppView.addSwitchButton("Switch to Alice", () -> switchScreenController.switchToUserScreen("Alice"));
        mainAppView.addSwitchButton("Switch to Charlie", () -> switchScreenController.switchToUserScreen("Charlie"));

        // Initialize ViewManager after all panels and buttons have been added
        new ViewManager(mainAppView.getCardPanel(), mainAppView.getCardLayout(), viewManagerModel);

        mainAppView.display();
    }
}