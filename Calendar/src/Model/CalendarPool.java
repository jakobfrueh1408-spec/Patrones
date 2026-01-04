package Model;

import java.util.ArrayList;
import java.util.List;

public class CalendarPool {
    private ArrayList<Calendar> calendars;

    @Override
    public String toString() {
        return "CalendarPool [calendars=" + calendars + "]";
    }
    public CalendarPool() {
        calendars = new ArrayList<Calendar>();
    }
    public void addCalendar(Calendar calendar) {
        calendars.add(calendar);
    }
    public Calendar getCalendar(int index) {
        if(calendars.size() ==0 ) {
            return null; 
        }
        return calendars.get(index);
    }
    public void removeCalendar(Calendar calendar) {
        int indexToRemove = calendars.indexOf(calendar);
        calendars.remove(indexToRemove);
    }
    public void modifyCalendar(int index) {
        Calendar tmp = calendars.get(index);
        //TO DO
    }

}
