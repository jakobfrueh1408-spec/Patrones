package Model;

import Database.CalendarTableManager;
import Database.DatabaseDAO;
import Database.EventNoteTableManager;

import java.util.Date;

/**
 * ZoomedInState represents the application state when the user has selected a specific day.
 * In this state, the user can perform detailed operations such as adding, removing,
 * or modifying events and notes for the chosen date.
 * <p>
 * This state primarily delegates data manipulation logic to the currently active
 * {@link Calendar} object and uses the {@link EventNoteTableManager} for database operations.
 * </p>
 */
public class ZoomedInState extends State<EventNoteTableManager> {

    /**
     * Constructs a ZoomedInState.
     * @param model    The singleton {@link Model} instance.
     * @param database The {@link EventNoteTableManager} used for event and note persistence.
     */
    public ZoomedInState(Model model, EventNoteTableManager database) {
        super(model, database);
    }

    /**
     * Adds a new event to the current calendar for the active date.
     * @param title               The title of the event.
     * @param description         A text description of the event.
     * @param label               The category label (e.g., sport, study).
     * @param lengthOfOccurrence The number of weeks the event should recur.
     */
    @Override
    public void addEvent(String title, String description, String label, int lengthOfOccurrence) {
        if (model.getCurrentUser().getCurrentCalendar() == null) {
            return;
        }
        model.getCurrentUser().getCurrentCalendar().addEvent(title, description, label, lengthOfOccurrence);
    }

    /**
     * Adds a new note to the current calendar for the active date.
     * @param title The title of the note.
     * @param text  The content of the note.
     */
    @Override
    public void addNote(String title, String text) {
        if (model.getCurrentUser().getCurrentCalendar() == null) {
            return;
        }
        model.getCurrentUser().getCurrentCalendar().addNote(title, text);
    }

    /**
     * Removes an event from the current day's list.
     * @param indexToRemove The index of the event in the day's event list.
     */
    @Override
    public void removeEvent(int indexToRemove) {
        model.getCurrentUser().getCurrentCalendar().removeEvent(indexToRemove);
    }

    /**
     * Removes a note from the current day's list.
     * @param indexToRemove The index of the note in the day's note list.
     */
    @Override
    public void removeNote(int indexToRemove) {
        model.getCurrentUser().getCurrentCalendar().removeNote(indexToRemove);
    }

    /**
     * Updates the description of an existing note for the current day.
     * @param indexToModify The index of the note to modify.
     * @param description   The new text content for the note.
     */
    @Override
    public void modifyNote(int indexToModify, String description) {
        model.getCurrentUser().getCurrentCalendar().modifyNote(indexToModify, description);
    }

    /**
     * Updates the description of an existing event for the current day.
     * @param indexToModify The index of the event to modify.
     * @param description   The new description text for the event.
     */
    @Override
    public void modifyEvent(int indexToModify, String description) {
        model.getCurrentUser().getCurrentCalendar().modifyEvent(indexToModify, description);
    }

    /**
     * Transitions the application back to the monthly/general calendar view.
     * Changes the application state to {@link SignedIn}.
     */
    @Override
    public void zoomOut() {
        model.setState(new SignedIn(model, new CalendarTableManager()));
    }

    /**
     * Returns the string representation of this state.
     * @return "ZoomedIn"
     */
    @Override
    public String toString() {
        return "ZoomedIn";
    }
}