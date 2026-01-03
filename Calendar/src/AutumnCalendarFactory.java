public class AutumnCalendarFactory implements CalendarFactory {
    public AutumnCalendarFactory() {}
    public Calendar createShortCalendar(String name){
        return new ShortAutumnCalendar(name);
    }
    public Calendar createLongCalendar(String name){
        return new LongAutumnCalendar(name);
    }
}
