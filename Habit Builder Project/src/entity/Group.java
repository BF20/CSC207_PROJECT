package entity;

import java.util.ArrayList;
import java.util.Map;

public class Group {
    private Map<String, User> users;

    private int group_score;

    public void add_user(User user) {
        this.users.put(user.GetUsername(), user);
    }

    public User get_user(String username) {
        return this.users.get(username);
    }

    public Map<String, User> GetAllUsers() {
        return this.users;
    }



}
