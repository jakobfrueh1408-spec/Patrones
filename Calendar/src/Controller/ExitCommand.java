package Controller;

/**
 * ExitCommand encapsulates the request to terminate the application.
 * It acts as a clean wrapper around the controller's exit logic, allowing
 * the shutdown process to be triggered from any part of the UI (e.g., menu items,
 * buttons, or window close events) using the Command pattern.
 */
public class ExitCommand extends Command {

    /**
     * Constructs an ExitCommand.
     * @param controller The {@link Controller} that contains the system exit logic.
     */
    public ExitCommand(Controller controller) {
        super(controller);
    }

    /**
     * Executes the command by calling {@code onExitClicked()} on the controller.
     * This typically leads to a {@code System.exit(0)} or a graceful cleanup
     * of database connections and resources.
     */
    @Override
    public void execute() {
        controller.onExitClicked();
    }
}