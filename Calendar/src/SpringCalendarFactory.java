public class SpringCalendarFactory implements CalendarFactory {
    public SpringCalendarFactory() {}
    public Calendar createShortCalendar(String name){
        return new ShortSpringCalendar(name);
    }
    public Calendar createLongCalendar(String name){
        return new LongSpringCalendar(name);
    }
}
