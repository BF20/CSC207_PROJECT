package interface_adapter.log_habit;

import use_case.switch_screens.SwitchScreenInputData;
import use_case.switch_screens.SwitchScreenInteractor;

public class SwitchScreenController {
    final SwitchScreenInteractor switchScreenInteractor;

    public SwitchScreenController(SwitchScreenInteractor switchScreenInteractor) {
        this.switchScreenInteractor = switchScreenInteractor;
    }
    
    public void switchToUserScreen(String username) {
        SwitchScreenInputData inputData = new SwitchScreenInputData(username);
        switchScreenInteractor.switchToUserScreen(inputData);
    }
}
