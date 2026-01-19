package Model;

import Database.CalendarTableManager;
import Database.EventNoteTableManager;
import Database.UserTableManager;

import java.time.LocalDate;
import java.util.Date;

/**
 * SignedIn represents the application state where a user is logged into their account.
 * In this state, the user can view their calendar list, rename or delete calendars,
 * navigate to a specific date, or transition to the calendar creation screen.
 * <p>
 * This state primarily interacts with the {@link CalendarTableManager} for
 * calendar-level database operations.
 * </p>
 */
public class SignedIn extends State<CalendarTableManager>{

    /**
     * Constructs a SignedIn state.
     * @param model    The singleton {@link Model} instance.
     * @param database The {@link CalendarTableManager} used for calendar data operations.
     */
    public SignedIn(Model model, CalendarTableManager database) {
        super(model,database);
    }

    /**
     * Terminates the current user session.
     * Clears the current user reference in the model and transitions back
     * to the {@link NotSignedIn} state.
     */
    @Override
    public void signOut(){
        model.setCurrentUser(null);
        model.setState(new NotSignedIn(model,new UserTableManager()));
    }

    /**
     * Navigates into a detailed view of a specific date.
     * Updates the current calendar's active date and transitions the
     * application to the {@link ZoomedInState}.
     * * @param year  The year of the selected date.
     * @param month The month of the selected date.
     * @param day   The day of the selected date.
     */
    @Override
    public void zoomIn(int year, int month, int day){
        // using the information passed by the controller to set the new currentDate
        LocalDate date = LocalDate.of(year, month, day);
        model.getCurrentUser().getCurrentCalendar().setCurrentDate(date);

        // setting the new state
        model.setState(new ZoomedInState(model, new EventNoteTableManager()));
    }

    /**
     * Transitions the application to the calendar creation interface.
     */
    @Override
    public void switchToCreateCalendar(){
        System.out.println("HALOOOOOOOO");
        model.setState(new CreateCalendarState(model, new CalendarTableManager()));
    }

    /**
     * Deletes a calendar from the user's account.
     * Removes the calendar from the database and the local user pool.
     * If no calendars remain, it automatically transitions the user
     * to the {@link CreateCalendarState}.
     * * @param indexToRemove The index of the calendar within the user's list.
     */
    @Override
    public void removeCalendar(int indexToRemove){
        // removing the calendar by index
        int removal_id = getCalendarIdByName(indexToRemove);
        System.out.printf("Trying to delete id : %d",removal_id);
        getDatabase().removeCalendar(removal_id);
        model.getCurrentUser().getCalendarPool().removeCalendar(indexToRemove);

        // checking the number of calendars in the calendarpool
        int numOfCals = model.getCurrentUser().getCalendarPool().getCalendars().size();
        if(numOfCals == 0) {
            model.setState(new CreateCalendarState(model,new CalendarTableManager()));
        }
    }

    /**
     * Modifies the title of the currently selected calendar.
     * Updates both the persistence layer and the local object.
     * * @param name The new name to be assigned to the calendar.
     */
    @Override
    public void modifyCalendar(String name){
        Calendar cal = model.getCurrentUser().getCurrentCalendar();
        int indx = model.getCurrentUser().getCalendarPool().getCalendars().indexOf(cal);
        int modifyId = getCalendarIdByName(indx);
        getDatabase().modifyCalendar(modifyId, name);
        model.getCurrentUser().getCurrentCalendar().setName(name);
    }

    /**
     * Handles application exit by delegating to the core model logic.
     */
    @Override
    public void exit(){
        model.exit();
    }

    /**
     * Helper method to retrieve the unique database ID of a calendar based
     * on its position in the local list.
     * * @param indx The index of the calendar in the local pool.
     * @return The unique database primary key (ID) for that calendar.
     */
    public int getCalendarIdByName(int indx){
        String nameOfRemoval = model.getCurrentUser().getCalendarPool().getCalendars().get(indx).getName();
        String userId = model.getCurrentUser().getIdNumber();
        Calendar cal = getDatabase().getCalendarByName(nameOfRemoval, userId);
        int cal_id = cal.getDb_id();
        return cal_id;
    }

    /**
     * Returns the string representation of this state.
     * @return "SignedIn"
     */
    @Override
    public String toString() {
        return "SignedIn";
    }
}