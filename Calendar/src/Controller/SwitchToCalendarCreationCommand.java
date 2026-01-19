package Controller;

/**
 * SwitchToCalendarCreationCommand encapsulates the request to navigate to
 * the calendar creation interface.
 * <p>
 * This command is typically triggered when a user clicks a "Create New Calendar"
 * button. It facilitates a state transition in the {@link Model} which, via
 * the Observer pattern, updates the View to display the calendar configuration form.
 * </p>
 */
public class SwitchToCalendarCreationCommand extends Command {

    /**
     * Constructs a SwitchToCalendarCreationCommand.
     * @param controller The {@link Controller} responsible for handling navigation logic.
     */
    public SwitchToCalendarCreationCommand(Controller controller) {
        super(controller);
    }

    /**
     * Executes the command by invoking {@code onCreateNewCalendarClicked()}
     * on the controller. This triggers the logic to switch the application
     * state to a view where new calendar details can be entered.
     */
    @Override
    public void execute() {
        controller.onCreateNewCalendarClicked();
    }
}