package use_cases.data_visualization;

import data_access.FileUserDataAccessObject;

import java.io.File;

public class GraphJavaScriptCreator {

    File JSfile;

    FileUserDataAccessObject dao;




    public GraphJavaScriptCreator (String JavaScriptfileDirectoryPath, FileUserDataAccessObject dao, String username){
        //the path you want to use is "Habit Builder Project/data_visualization/" + username + "Graph.js"
        JSfile = new File(JavaScriptfileDirectoryPath + username + "Graph.js");
        // create a new JS file for each user
        //write it to JS


        // create JS and convert the YAML into JS code that can be read by quick chart api

    }

    // from each js file, there should be a

}
