package Model;

import Database.DatabaseDAO;

import java.util.Date;

/**
 * State is the abstract base class for all application states in the Model.
 * It follows the State Design Pattern, defining a common interface for all
 * possible user actions.
 * <p>
 * Subclasses represent specific states (e.g., NotSignedIn, SignedIn, ZoomedIn)
 * and override only the methods relevant to that state's behavior.
 * </p>
 *
 * @param <T> The type of Database Data Access Object used by the specific state.
 */
public abstract class State<T extends DatabaseDAO> {
    protected Model model;
    private T database;

    /**
     * Constructs a State with a reference to the core Model and a specific database manager.
     * @param model    The singleton {@link Model} instance.
     * @param database The Data Access Object used for persistence in this state.
     */
    public State(Model model, T database) {
        this.database = database;
        this.model = model;
    }

    /**
     * Sets the model reference for this state.
     * @param model The {@link Model} instance.
     */
    public void setModel(Model model) {
        this.model = model;
    }

    /**
     * Retrieves the database manager associated with this state.
     * @return The database DAO instance.
     */
    public T getDatabase() {
        return database;
    }

    /**
     * Sets the database manager for this state.
     * @param database The database DAO instance.
     */
    public void setDatabase(T database) {
        this.database = database;
    }

    /**
     * Attempts to authenticate a user. (Implemented in NotSignedInState).
     * @param name     The username.
     * @param password The password.
     * @throws Exception If authentication fails.
     */
    public void signIn(String name, String password) throws Exception {}

    /**
     * Registers a new user. (Implemented in NotSignedInState).
     * @param name     The username.
     * @param password The password.
     * @param birthday The user's birthday string.
     */
    public void register(String name, String password, String birthday) {}

    /**
     * Terminates the current user session. (Implemented in SignedInState).
     */
    public void signOut(){}

    /**
     * Navigates into a detailed view of a specific date. (Implemented in SignedInState).
     * @param year  The year selected.
     * @param month The month selected.
     * @param day   The day selected.
     */
    public void zoomIn(int year, int month, int day){}

    /**
     * Deletes a calendar from the user's collection. (Implemented in SignedInState).
     * @param indexToRemove The index of the calendar in the list.
     */
    public void removeCalendar(int indexToRemove){}

    /**
     * Renames the current calendar. (Implemented in SignedInState).
     * @param newTitle The new name for the calendar.
     */
    public void modifyCalendar( String newTitle){}

    /**
     * Transitions the application to the calendar creation view. (Implemented in SignedInState).
     */
    public void switchToCreateCalendar(){}

    /**
     * Creates and persists a new calendar. (Implemented in SignedIn and CreateCalendar states).
     * @param name   The calendar title.
     * @param length The duration of the stay.
     * @param season The starting semester.
     * @param year   The starting year.
     */
    public void addCalendar(String name, int length,  String season, int year){}

    /**
     * Transitions the application back to the main signed-in dashboard. (Implemented in CreateCalendarState).
     */
    public void switchToSignedIn() {}

    /**
     * Navigates back from a day view to the month view. (Implemented in ZoomedInState).
     */
    public void zoomOut(){}

    /**
     * Adds an event to the selected date. (Implemented in ZoomedInState).
     * @param title               The event title.
     * @param description         The event description.
     * @param label               The category label (e.g., sport, study).
     * @param lengthOfOccurrence The frequency/duration of the event.
     */
    public void addEvent(String title, String description, String label, int lengthOfOccurrence){}

    /**
     * Removes an event from the selected date. (Implemented in ZoomedInState).
     * @param indexToRemove The index of the event to delete.
     */
    public void removeEvent(int indexToRemove){}

    /**
     * Updates the text of an existing event. (Implemented in ZoomedInState).
     * @param indexToRemove The index of the event to modify.
     * @param description   The new description text.
     */
    public void modifyEvent(int indexToRemove, String description){}

    /**
     * Adds a note to the selected date. (Implemented in ZoomedInState).
     * @param title The note title.
     * @param text  The note content.
     */
    public void addNote(String title, String text){}

    /**
     * Removes a note from the selected date. (Implemented in ZoomedInState).
     * @param indexToRemove The index of the note to delete.
     */
    public void removeNote(int indexToRemove){}

    /**
     * Updates the content of an existing note. (Implemented in ZoomedInState).
     * @param indexToModify The index of the note to modify.
     * @param description   The new content text.
     */
    public void modifyNote(int indexToModify, String description){}

    /**
     * Handles application shutdown procedures. (Implemented in all states).
     */
    public void exit(){}

}