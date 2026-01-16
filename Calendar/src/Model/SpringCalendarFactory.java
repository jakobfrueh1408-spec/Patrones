package Model;

public class SpringCalendarFactory implements CalendarFactory {
    public SpringCalendarFactory() {}
    public Calendar createShortCalendar(String name, int year){
        return new ShortSpringCalendar(name, year);
    }
    public Calendar createLongCalendar(String name, int year){
        return new LongSpringCalendar(name, year);
    }
}
