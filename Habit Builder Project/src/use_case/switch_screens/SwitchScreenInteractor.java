package use_case.switch_screens;

import view.ViewManagerModel;

public class SwitchScreenInteractor implements SwitchScreenInputBoundary{
    private final ViewManagerModel viewManagerModel;

    public SwitchScreenInteractor(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;


    }

    @Override
    public void switchToUserScreen(SwitchScreenInputData inputData) {
        String username = inputData.getUsername();
        this.viewManagerModel.setActiveView(username);
    }
}
