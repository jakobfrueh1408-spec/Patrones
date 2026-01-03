import java.util.ArrayList;
import java.util.List;

public class CalendarPool {
    private List<Calendar> calendars;
    private CalendarFactory calendarFactory;
    public CalendarPool(CalendarFactory calendarFactory) {
        calendars = new ArrayList<Calendar>();
        this.calendarFactory = calendarFactory;
    }
    public void addCalendar(Calendar calendar) {
        calendars.add(calendar);
    }
    public Calendar getCalendar(int index) {
        return calendars.get(index);
    }
    public void removeCalendar(int index) {
        calendars.remove(index);
    }
    public void modifyCalendar(int index) {
        Calendar tmp = calendars.get(index);
        //TO DO
    }
}
