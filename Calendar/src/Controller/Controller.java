package Controller;

import Model.*;
import View.*;

import javax.swing.*;
import java.awt.*;

/**
 * The Controller class acts as the intermediary between the View and the Model.
 * It handles user input from the UI components and triggers the corresponding
 * business logic in the {@link Model}.
 * <p>
 * It maintains references to the application's layout components to manage
 * navigation between different screens (Cards).
 * </p>
 */
public class Controller {
    private Model model;
    private View view;
    private JPanel contentPane;
    private CardLayout cardLayout;

    /**
     * Constructs a Controller with a reference to the data model.
     * @param model The application's core {@link Model}.
     */
    public Controller(Model model) {
        this.model = model;
    }

    //******************************************************** Main Menu State ******************************************************************//

    /**
     * Shuts down the application.
     */
    public void onExitClicked(){
        System.exit(0);
    }

    /**
     * Processes a sign-in request by passing credentials to the model.
     * @param name     The username.
     * @param password The user password.
     * @throws Exception if authentication logic in the model fails.
     */
    public void onSignInClicked(String name, String password) throws Exception {
        model.signIn(name, password);
    }

    /**
     * Processes a registration request for a new user.
     * @param name     The username.
     * @param password The password.
     * @param birthday The birth date string.
     */
    public void onRegisterClicked(String name, String password, String birthday){
        model.register(name, password, birthday);
    }

    /**
     * Triggers the addition of a new calendar to the current user's profile.
     * @param name   Title of the calendar.
     * @param length Duration in semesters.
     * @param start  Starting season (e.g., Autumn).
     * @param year   Starting year.
     */
    public void onAddCalendarClicked(String name, int length, String start, int year){
        model.addCalendar(name, length, start, year);
    }


    //******************************************************** Signed In State ******************************************************************//

    /**
     * Logs the current user out of the application.
     */
    public void onSignOutClicked(){
        model.signOut();
    }

    /**
     * Requests the removal of a specific calendar.
     * @param indexToRemove The index of the calendar in the user's list.
     */
    public void onRemoveCalendarClicked(int indexToRemove){
        model.removeCalendar(indexToRemove);
    }

    /**
     * Requests a title change for the currently active calendar.
     * @param newtitle The new name for the calendar.
     */
    public void onModifyCalendarClicked(String newtitle){
        model.modifyCalendar(newtitle);
    }

    /**
     * Transitions the view into a detailed daily view for a specific date.
     * @param year  Selected year.
     * @param month Selected month.
     * @param day   Selected day.
     */
    public void onZoomInClicked(int year, int month, int day){
        model.zoomIn(year, month, day);
    }

    /**
     * Changes the application state to facilitate the creation of a new calendar.
     */
    public void onCreateNewCalendarClicked(){
        model.switchToCreateCalendar();
    }


    //******************************************************** Zoomed In State ******************************************************************//

    /**
     * Adds a note to the currently selected date.
     * @param title Title of the note.
     * @param text  Content text of the note.
     */
    public void onAddNoteClicked(String title, String text){
        model.addNote(title, text);
    }

    /**
     * Removes a note from the current date's list.
     * @param index The index of the note to remove.
     */
    public void onRemoveNoteClicked(int index){
        model.removeNote(index);
    }

    /**
     * Updates the content of an existing note.
     * @param index The index of the note to modify.
     * @param text  The new content for the note.
     */
    public void onModifyNoteClicked(int index, String text){
        model.modifyNote(index, text);
    }

    /**
     * Adds an event to the selected date with optional recurrence.
     * @param title               Title of the event.
     * @param description         Description of the event.
     * @param label               Category label.
     * @param lengthOfOccurrence Number of weeks the event recurs.
     */
    public void onAddEventClicked(String title, String description, String label, int lengthOfOccurrence){
        // Subtraction of 1 aligns human-readable count with zero-based logic in model
        model.addEvent(title, description, label, lengthOfOccurrence-1);
    }

    /**
     * Removes an event from the current date.
     * @param index The index of the event to remove.
     */
    public void onRemoveEventClicked(int index){
        model.removeEvent(index);
    }

    /**
     * Updates the details of an existing event.
     * @param index The index of the event to modify.
     * @param text  The new description or title for the event.
     */
    public void onModifyEventClicked(int index, String text){
        model.modifyEvent(index, text);
    }

    /**
     * Returns from the day view back to the main calendar view.
     */
    public void onZoomOutClicked(){
        model.zoomOut();
    }


    //******************************************************** Helping Functions ******************************************************************//

    /**
     * Utility method to fetch a calendar by index from the current user.
     * @param index Index in the pool.
     * @return The {@link Calendar} object.
     */
    public Calendar getCalendar(int index){
        return model.getCurrentUser().getCalendarPool().getCalendars().get(index);
    }

    /**
     * Sets the active calendar for the current user session.
     * @param calendar The {@link Calendar} to activate.
     */
    public void setCurrentCalendar(Calendar calendar){
        model.getCurrentUser().setCurrentCalendar(calendar);
    }

    /**
     * Sets the reference to the main UI container.
     * @param contentPane The {@link JPanel} acting as the container.
     */
    public void setContentPane(JPanel contentPane) {
        this.contentPane = contentPane;
    }

    /**
     * Sets the layout manager used for switching screens.
     * @param cardLayout The {@link CardLayout} instance.
     */
    public void setCardLayout(CardLayout cardLayout) {
        this.cardLayout = cardLayout;
    }

    /**
     * Assigns the View reference to the controller.
     * @param view The {@link View} instance.
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * Retrieves the data model.
     * @return The {@link Model} instance.
     */
    public Model getModel(){
        return model;
    }
}