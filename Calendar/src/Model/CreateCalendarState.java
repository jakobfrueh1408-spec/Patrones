package Model;

import Database.CalendarTableManager;
import Database.DatabaseDAO;

/**
 * CreateCalendarState represents the application state where the user is creating a new calendar.
 * This state handles the logic for calendar instantiation and ensures the user is redirected
 * to the main dashboard (SignedIn state) once a calendar is successfully added.
 */
public class CreateCalendarState extends State {

    /**
     * Constructs a CreateCalendarState instance.
     * * @param model    The singleton {@link Model} instance.
     * @param database The Data Access Object used for database operations in this state.
     */
    public CreateCalendarState(Model model, DatabaseDAO database){
        super(model, database);
    }

    /**
     * Creates a new calendar for the current user and transitions the view back to the dashboard.
     * Delegates the creation logic to the {@link User} class.
     * * @param name   The title of the new calendar.
     * @param length The duration of the calendar in semesters.
     * @param season The starting season (e.g., "Autumn" or "Spring").
     * @param year   The starting year for the calendar.
     */
    @Override
    public void addCalendar(String name, int length, String season, int year){
        model.getCurrentUser().createCalendar(name, length, season, year);
        switchToSignedIn();
    }

    /**
     * Transitions the application state from calendar creation back to the main signed-in view.
     * If this is the user's first calendar, it automatically sets it as the active (current) calendar.
     */
    @Override
    public void switchToSignedIn() {
        int numOfCals = model.getCurrentUser().getCalendarPool().getCalendars().size();
        // If it's the only calendar, set it as active immediately
        if(numOfCals == 1){
            model.getCurrentUser().setCurrentCalendar(model.getCurrentUser().getCalendarPool().getCalendars().get(0));
        }
        model.setState(new SignedIn(model, new CalendarTableManager()));
    }

    /**
     * Handles application exit procedures while in the calendar creation state.
     */
    @Override
    public void exit(){}

    /**
     * Returns the name of this state.
     * @return A string representing the state: "CreateCalendar".
     */
    public String toString(){
        return "CreateCalendar";
    }
}