package use_cases.data_entry;

public class DataEntryInteractor implements DataEntryInputBoundary{
    @Override
    public void execute(DataEntryInputData dataEntryInputData) {
        // if they enter the same task name during the week:
        // add amount into existing task name
        // else：
        // create new task, and add hours to it
        // data access
        // output data
        // display view "successfully added"
    }
}
