package use_case.mainwindow;

import entity.User;

import java.util.ArrayList;
import java.util.Map;

public interface MainWindowDataAccessInterface {

    Map<String, User> get_all_users();
    /*
    Get all the users in the database
     */


    User get_user(String username);
    /*
    Get a specific user in the database by their username
     */



    void save_user(User user) throws Exception;
    /*
    Save a user to the database
     */


}
