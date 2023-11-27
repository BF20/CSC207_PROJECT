package entity;

import java.util.ArrayList;
import java.util.Map;

public interface UserFactoryInterface {

    User create(String username, boolean admin, ArrayList<Habit> completed_habits);

}
