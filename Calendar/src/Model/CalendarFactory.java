package Model;

public interface CalendarFactory {
    public Calendar createShortCalendar(String name,int year);
    public Calendar createLongCalendar(String name, int year);
}
