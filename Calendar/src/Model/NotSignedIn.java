package Model;

import Database.CalendarTableManager;
import Database.DatabaseDAO;
import Database.EventNoteTableManager;
import Database.UserTableManager;

import java.util.ArrayList;

/**
 * NotSignedIn represents the state of the application when no user is currently authenticated.
 * This class handles the logic for user login (sign-in) and account registration.
 * <p>
 * During sign-in, this state is responsible for bootstrapping the user's data, including
 * loading their calendars, events, and notes from the persistence layer.
 * </p>
 */
public class NotSignedIn extends State<UserTableManager> implements signInInterface {

    /**
     * Constructs a NotSignedIn state.
     * * @param model    The singleton {@link Model} instance.
     * @param database The {@link UserTableManager} used for authentication queries.
     */
    public NotSignedIn(Model model, UserTableManager database) {
        super(model, database);
    }

    /**
     * Authenticates a user and loads their entire application profile.
     * <p>
     * Logic flow:
     * 1. Verifies credentials against the database.
     * 2. Loads all associated calendars for the user.
     * 3. If no calendars exist, transitions to {@link CreateCalendarState}.
     * 4. If calendars exist, loads events/notes for the primary calendar and transitions to {@link SignedIn} state.
     * </p>
     * * @param userName The username entered by the user.
     * @param password The password entered by the user.
     * @throws Exception if the user is not found or credentials are invalid.
     */
    @Override
    public void signIn(String userName, String password) throws Exception {
            ArrayList<User> listToTraverse = model.getUserPool().getUsers();

            // Authenticate via Database
            User loggedUser = getDatabase().getUserByLogin(userName, password);
            CalendarTableManager calendarTableManager = new CalendarTableManager();
            EventNoteTableManager eventNoteTableManager = new EventNoteTableManager();
            //set extracted user information (obtained from getUserByLogin()) into
            // the defined data structures of the model
            if (loggedUser != null) {
                // Set the active user in the model
                model.setCurrentUser(loggedUser);

                // Load user's calendars
                ArrayList<Calendar> userCalendars = calendarTableManager.loadAllCalendarsForUser(loggedUser.getIdNumber());
                model.getCurrentUser().getCalendarPool().setCalendars(userCalendars);

                int numOfUserCals = model.getCurrentUser().getCalendarPool().getCalendars().size();

                if (numOfUserCals == 0) {
                    // New user flow: must create a calendar first
                    model.setState(new CreateCalendarState(model, new CalendarTableManager()));
                } else {
                    // Returning user flow: load existing data for the first calendar
                    model.getCurrentUser().setCurrentCalendar(userCalendars.get(0));

                    ArrayList<Event> eventCalendarList = eventNoteTableManager.fetchEvents(model.getCurrentUser().getCurrentCalendar().getDb_id());
                    ArrayList<Note> noteCalendarList = eventNoteTableManager.fetchNotes(model.getCurrentUser().getCurrentCalendar().getDb_id());

                    model.getCurrentUser().getCurrentCalendar().setEvents(eventCalendarList);
                    model.getCurrentUser().getCurrentCalendar().setNotes(noteCalendarList);

                    model.setState(new SignedIn(model, new CalendarTableManager()));
                }

            } else {
                throw new Exception("Not registered user!");
            }
    }

    /**
     * Registers a new user into the system.
     * Persists the user data to the database and adds them to the runtime user pool.
     * * @param name     The desired username.
     * @param password The desired password.
     * @param birthday The user's birthday.
     */
    @Override
    public void register(String name, String password, String birthday) {
        UserTableManager usertable = new UserTableManager();
        User user = new User(name, password, birthday);
        usertable.register(user);
        model.getUserPool().addUser(user);
        System.out.println("Users in the userpool" + model.getUserPool());
    }

    /**
     * Handles application exit by delegating to the model's exit logic.
     */
    @Override
    public void exit() {
        model.exit();
    }

    /**
     * Returns the name of this state.
     * @return String "NotSignedIn".
     */
    @Override
    public String toString() {
        return "NotSignedIn";
    }
}