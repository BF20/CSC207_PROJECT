package entity;

import java.util.ArrayList;
import java.util.Map;

public class UserFactory implements UserFactoryInterface {

    @Override
    public User create(String username, boolean admin,  ArrayList<Habit> completed_habits) {
        return new User(username, admin, completed_habits);
    }

}
