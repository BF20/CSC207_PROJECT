package use_case.log_habit;

import entity.User;

public interface LogHabitDataAccessInterface {
    void save_user(User user) throws Exception;

    User get_user(String username);
}
