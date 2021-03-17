package model.businessLayer;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateOrder {

    private int day, month, year, hour, minute;

    public DateOrder(){
        Calendar cal= Calendar.getInstance(TimeZone.getTimeZone("Europe/Bucharest"));
        Date date = new Date();
        cal.setTime(date);
        this.day = cal.get(Calendar.DAY_OF_MONTH);
        this.month = cal.get(Calendar.MONTH)+1;
        this.year = cal.get(Calendar.YEAR);
        this.hour = cal.get(Calendar.HOUR_OF_DAY);
        this.minute = cal.get(Calendar.MINUTE);
    }

    public int getCurrentDay() {
        return day;
    }

    public int getCurrentMonth() {
        return month;
    }

    public int getCurrentYear() {
        return year;
    }

    public int getYear() {
        return year;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    @Override
    public String toString() {
        return  day +
                "/" + month +
                "/" + year +
                " " + hour +
                ":" + minute;
    }
}
