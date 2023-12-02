package use_cases.data_visualization;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtils {

    public List<LocalDate> getLast7Days() {
        List<LocalDate> last7Days = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        Date today = calendar.getTime();


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


        last7Days.add(LocalDate.parse(dateFormat.format(today)));


        for (int i = 1; i < 7; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, -1); // Subtract a day
            Date previousDay = calendar.getTime();
            last7Days.add(LocalDate.parse(dateFormat.format(previousDay)));
        }

        return last7Days;
    }

    public void main(String[] args) {
        List<LocalDate> last7Days = getLast7Days();
//        for (String date : last7Days) {
//            System.out.println(date);
//        }
        System.out.println(last7Days);
    }
}

