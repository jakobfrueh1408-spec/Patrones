package Model;

public class AutumnCalendarFactory implements CalendarFactory {
    public AutumnCalendarFactory() {}
    public Calendar createShortCalendar(String name, int year){
        return new ShortAutumnCalendar(name, year);
    }
    public Calendar createLongCalendar(String name, int year){
        return new LongAutumnCalendar(name, year);
    }
}
