package Model;

import Controller.ModelObserver;
import Database.UserTableManager;

import java.util.ArrayList;

/**
 * The Model class acts as the central data hub and state manager for the application.
 * It follows the Singleton pattern to ensure a single source of truth.
 * <p>
 * It maintains the current user, the user pool, and delegates behavior to a
 * {@link State} object. It also notifies registered {@link ModelObserver}s
 * whenever a change occurs that requires a UI refresh.
 * </p>
 */
public class Model {
    private UserPool userPool;
    private User currentUser;
    private State state;
    private static Model instance;
    private final ArrayList<ModelObserver> observers = new ArrayList<>();

    /**
     * Private constructor to enforce the Singleton pattern.
     * Initializes the user pool and sets the initial state to NotSignedIn.
     */
    private Model() {
        userPool = new UserPool();
        currentUser = null;
        state = new NotSignedIn(this, new UserTableManager());
    }

    /**
     * Returns the single instance of the Model, creating it if it doesn't exist.
     * @return The singleton Model instance.
     */
    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    /**
     * Retrieves the pool of all registered users.
     * @return The {@link UserPool} instance.
     */
    public UserPool getUserPool() {
        return userPool;
    }

    /**
     * Retrieves the currently authenticated user.
     * @return The {@link User} currently signed in, or null if none.
     */
    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Transitions the application to a new state and notifies observers of the change.
     * @param state The new {@link State} to transition to.
     */
    public void setState(State state) {
        this.state = state;
        notifyObservers();
    }

    /**
     * Updates the currently active user.
     * @param currentUser The {@link User} to set as the current user.
     */
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    /**
     * Returns a string representation of the current application state.
     * @return The name of the current state.
     */
    public String getState() {
        return state.toString();
    }

    /*********************************************************************************************************************/

    /**
     * Attempts to sign in a user by delegating to the current state.
     * @param name     The username.
     * @param password The user's password.
     * @throws Exception If authentication fails or is not permitted in the current state.
     */
    public void signIn(String name, String password) throws Exception {
        state.signIn(name, password);
    }

    /**
     * Registers a new user by delegating to the current state.
     * @param name     The username.
     * @param password The password.
     * @param birthday The user's birth date.
     */
    public void register(String name, String password, String birthday) {
        state.register(name, password, birthday);
    }

    /**
     * Signs the current user out by delegating to the current state.
     */
    public void signOut() {
        state.signOut();
    }

    /**
     * Zooms into a specific day view by delegating to the current state.
     * @param year  The year of the date.
     * @param month The month of the date.
     * @param day   The day of the date.
     */
    public void zoomIn(int year, int month, int day) {
        state.zoomIn(year, month, day);
    }

    /**
     * Removes a calendar from the current user's pool and notifies observers.
     * @param indexToRemove The index of the calendar to remove.
     */
    public void removeCalendar(int indexToRemove) {
        state.removeCalendar(indexToRemove);
        notifyObservers();
    }

    /**
     * Modifies the title of the current calendar and notifies observers.
     * @param title The new title for the calendar.
     */
    public void modifyCalendar( String title) {
        state.modifyCalendar( title);
        notifyObservers();
    }

    /**
     * Adds a new calendar to the current user by delegating to the current state.
     * @param name   The title of the calendar.
     * @param length The duration of the semester/stay.
     * @param season The starting semester (e.g., Spring/Autumn).
     * @param year   The starting year.
     */
    public void addCalendar(String name, int length, String season, int year) {
        state.addCalendar(name, length, season, year);
    }

    /**
     * Returns from the day view to the month view by delegating to the current state.
     */
    public void zoomOut() {
        state.zoomOut();
    }

    /**
     * Adds a new event to the current day and notifies observers.
     * @param title               The event title.
     * @param description         The event description.
     * @param label               The category label.
     * @param lengthOfOccurrence The duration/frequency of the event.
     */
    public void addEvent(String title, String description, String label, int lengthOfOccurrence) {
        state.addEvent(title, description, label, lengthOfOccurrence);
        notifyObservers();
    }

    /**
     * Switches the application view to the calendar creation screen.
     */
    public void switchToCreateCalendar(){
        state.switchToCreateCalendar();
    }

    /**
     * Adds a note to the current day and notifies observers.
     * @param title The note title.
     * @param text  The content of the note.
     */
    public void addNote(String title, String text) {
        state.addNote(title, text);
        notifyObservers();
    }

    /**
     * Removes an event from the current day and notifies observers.
     * @param indexToRemove The index of the event in the current day's list.
     */
    public void removeEvent(int indexToRemove) {
        state.removeEvent(indexToRemove);
        notifyObservers();
    }

    /**
     * Removes a note from the current day and notifies observers.
     * @param indexToRemove The index of the note in the current day's list.
     */
    public void removeNote(int indexToRemove) {
        state.removeNote(indexToRemove);
        notifyObservers();
    }

    /**
     * Updates the description of an existing note and notifies observers.
     * @param indexToRemove The index of the note to modify.
     * @param description   The new text for the note.
     */
    public void modifyNote(int indexToRemove, String description) {
        state.modifyNote(indexToRemove, description);
        notifyObservers();
    }

    /**
     * Updates the description of an existing event and notifies observers.
     * @param indexToModify The index of the event to modify.
     * @param description   The new description for the event.
     */
    public void modifyEvent(int indexToModify, String description) {
        state.modifyEvent(indexToModify, description);
        notifyObservers();
    }

    /**
     * Handles application exit procedures by delegating to the current state.
     */
    public void exit() {
        state.exit();
    }

    /**
     * Registers an observer to be notified of model changes.
     * @param observer The {@link ModelObserver} to add.
     */
    public void addObserver(ModelObserver observer) {
        observers.add(observer);
    }

    /**
     * Notifies all registered observers that the model has changed.
     */
    private void notifyObservers() {
        for (ModelObserver observer : observers) {
            observer.modelChanged();
        }
    }
}