import java.lang.reflect.Array;
import java.util.ArrayList;

public class DataReader {
    public static ArrayList<Array> read_csv_file(String file_path) {

        return null;

    }
}

 /* The idea here is that we have a csv file of 8 fields:
 * user ("string")
 * habit ("string")
 * habit_type (a string which is an element of the set of valid habit types)
 * habit_start_time (an instance of the java LocalDateTime class)
 * habit_end_time (an instance of the java LocalDateTime class)
 * periodic (a bool which represents whether the habit is periodic.)
 * number_of_periods (how many times the habit repeats). It must be greater than 1.
 * period_separation (an instance of the java LocalDateTime class. It is the distance between the periods).
 * */




