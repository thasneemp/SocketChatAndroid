package examples.muhammed.com.socketchatandroid.date_manipulation;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by thasneem on 30/3/16.
 */
public class DateImpl {
    public static String getFormattedTime(String time) {
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(Long.parseLong(time));
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int am_pm = calendar.get(Calendar.AM_PM);
        return (hour == 0 ? "12" : hour) + ":" + (minute < 10 ? "0" + minute : minute) + " " + (am_pm == 1 ? "PM" : "AM");
    }
}
