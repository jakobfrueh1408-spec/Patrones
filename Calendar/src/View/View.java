package View;

import Controller.Controller;
import Controller.ModelObserver;
import Model.*;

import javax.swing.*;

/**
 * The View class serves as the main entry point for the user interface logic.
 * It implements the {@link ModelObserver} interface to react to changes in the
 * underlying data model and coordinates with the {@link CalendarForm} to
 * update the visual state of the application.
 */
public class View implements ModelObserver {
    private Controller controller;
    private CalendarForm calendarForm;

    /**
     * Constructs a View instance and establishes connections between the
     * controller, the model, and the graphical components.
     * * @param controller The application controller that handles business logic and navigation.
     */
    public View(Controller controller) {
        this.controller = controller;
        // Registers this view as an observer of the model to enable reactive updates
        controller.getModel().addObserver(this);
        calendarForm = new CalendarForm(this);

        // Pass UI container references to the controller for navigation management
        controller.setCardLayout(calendarForm.getCardLayout());
        controller.setContentPane(calendarForm.getContentPane());
        controller.setView(this);
    }

    /**
     * Callback method triggered by the model whenever its internal state changes.
     * This implementation forces a full UI refresh.
     */
    @Override
    public void modelChanged() {
        this.refreshView();
    }

    /**
     * Synchronizes the UI components with the current data in the model.
     * This method handles:
     * <ul>
     * <li>User authentication state (showing login/main menu).</li>
     * <li>Calendar selection and population of events/notes.</li>
     * <li>View state transitions (ZoomedIn, SignedIn, etc.) via CardLayout.</li>
     * </ul>
     */
    private void refreshView(){
        if (controller.getModel().getCurrentUser() == null) {
            calendarForm.showMainMenuPanel();
            return;
        }

        Calendar current = controller.getModel().getCurrentUser().getCurrentCalendar();
        if (current == null) {
            calendarForm.showCreateCalendarPanel();
            return;
        }

        // Update calendar data displays
        calendarForm.setCurrentCalendar(current);
        calendarForm.refreshCalendarList(controller.getModel().getCurrentUser().getCalendarPool().getCalendars());
        calendarForm.setDisplayedMonth(current.getCurrentDate());
        calendarForm.refreshYearMonthLabels();
        calendarForm.refreshDayPanel();
        calendarForm.refreshEventsList(current.getCurrentDayEventList());
        calendarForm.refreshNotesList(current.getCurrentDayNoteList());
        calendarForm.repaintMonthView();

        // Determine which panel to show based on the application state
        switch(controller.getModel().getState()){
            case "MainMenu":
                calendarForm.showMainMenuPanel();
                break;
            case "CreateCalendar":
                calendarForm.showCreateCalendarPanel();
                break;
            case "SignedIn":
                calendarForm.showSignedInPanel();
                break;
            case "ZoomedIn":
                calendarForm.showZoomedInPanel();
                break;
            default:
                calendarForm.showMainMenuPanel();
        }
    }

    /**
     * Provides access to the application controller.
     * * @return The {@link Controller} associated with this view.
     */
    public Controller getController() {
        return controller;
    }

}