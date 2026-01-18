package Model;

import java.util.ArrayList;
import java.util.List;

public class CalendarPool {


    private ArrayList<Calendar> calendars;

    public ArrayList<Calendar> getCalendars() {
        return calendars;
    }
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
    public void removeCalendar(int index) {
        calendars.remove(index);
    }
    public void setCalendars(ArrayList<Calendar> calendars) {
        this.calendars = calendars;
    }
    public Calendar getCalendarByName(String name) {
        Calendar calendar = null;
        for (Calendar cal: calendars) {
            if(name.equals(cal.getName())){
                calendar = cal;
                return calendar;
            }

        }
        return calendar;
    }
    public Calendar getCalendarByIndex(int index) {
        Calendar calendar = calendars.get(index);
        return calendar;
    }
    public boolean nameAvailable(String name){
        for(Calendar cal : calendars) {
            if(name.equals(cal.getName())){
                return false;
            }
        }
        return true;
    }
}
