package app;

import data_access.GroupGoalDAO;
import entity.GroupGoal;
import entity.StudyHabitFactory;
import entity.UserFactory;
import interface_adapter.GroupGoal.GroupGoalController;
import interface_adapter.GroupGoal.GroupGoalPresenter;
import interface_adapter.GroupGoal.GroupGoalViewModel;
import interface_adapter.log_habit.LogHabitController;
import use_case.group_goal.GroupGoalDataAccessInterface;
import use_case.group_goal.GroupGoalInputBoundary;
import use_case.group_goal.GroupGoalInteractor;
import use_case.group_goal.GroupGoalOutputBoundary;
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
        mainAppView.addGroupGoalView(groupGoalController);
        mainAppView.addGroupGoalButton();



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




}}
