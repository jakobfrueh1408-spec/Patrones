package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * CalendarPool serves as a container and manager for multiple {@link Calendar} objects.
 * It provides methods to manipulate the collection of calendars, verify naming availability,
 * and retrieve specific calendar instances by index or name.
 */
public class CalendarPool {

    /** The list of calendars managed by this pool. */
    private ArrayList<Calendar> calendars;

    /**
     * Retrieves the entire list of calendars.
     * @return An {@link ArrayList} of {@link Calendar} objects.
     */
    public ArrayList<Calendar> getCalendars() {
        return calendars;
    }

    /**
     * Returns a string representation of the CalendarPool.
     * @return A string containing the list of calendars.
     */
    @Override
    public String toString() {
        return "CalendarPool [calendars=" + calendars + "]";
    }

    /**
     * Constructs an empty CalendarPool.
     */
    public CalendarPool() {
        calendars = new ArrayList<Calendar>();
    }

    /**
     * Adds a new calendar to the pool.
     * @param calendar The {@link Calendar} instance to be added.
     */
    public void addCalendar(Calendar calendar) {
        calendars.add(calendar);
    }

    /**
     * Retrieves a calendar at the specified index.
     * @param index The index of the calendar to retrieve.
     * @return The {@link Calendar} at the given index, or null if the pool is empty.
     */
    public Calendar getCalendar(int index) {
        if(calendars.size() == 0) {
            return null;
        }
        return calendars.get(index);
    }

    /**
     * Removes the calendar at the specified index from the pool.
     * @param index The index of the calendar to be removed.
     */
    public void removeCalendar(int index) {
        calendars.remove(index);
    }

    /**
     * Replaces the current list of calendars with a new list.
     * @param calendars The new {@link ArrayList} of {@link Calendar} objects.
     */
    public void setCalendars(ArrayList<Calendar> calendars) {
        this.calendars = calendars;
    }

    /**
     * Searches for a calendar by its name.
     * @param name The name of the calendar to find.
     * @return The {@link Calendar} with the matching name, or null if not found.
     */
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

    /**
     * Retrieves a calendar by its index.
     * @param index The index of the calendar.
     * @return The {@link Calendar} at that index.
     */
    public Calendar getCalendarByIndex(int index) {
        Calendar calendar = calendars.get(index);
        return calendar;
    }

    /**
     * Checks if a specific name is available (not already used by another calendar in the pool).
     * @param name The name to check.
     * @return true if the name is not currently in use; false otherwise.
     */
    public boolean nameAvailable(String name){
        for(Calendar cal : calendars) {
            if(name.equals(cal.getName())){
                return false;
            }
        }
        return true;
    }
}