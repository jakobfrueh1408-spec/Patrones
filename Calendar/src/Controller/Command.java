package Controller;

/**
 * The Command class is the abstract base for the Command Design Pattern.
 * It encapsulates a request as an object, thereby letting you parameterize
 * clients with different requests and support undoable operations.
 * <p>
 * Each concrete subclass represents a specific user action (e.g., Sign In,
 * Add Event) and contains the logic necessary to trigger that action
 * via the {@link Controller}.
 * </p>
 */
public abstract class Command {
    /** * Reference to the controller that will perform the actual
     * business logic associated with the command.
     */
    protected Controller controller;

    /**
     * Constructs a Command with a reference to the application controller.
     * @param controller The {@link Controller} instance used to execute the command logic.
     */
    public Command(Controller controller) {
        this.controller = controller;
    }

    /**
     * Executes the specific logic defined by the concrete command implementation.
     * This method acts as the bridge between the UI trigger and the Controller's logic.
     * @throws Exception if the command execution encounters a logic or data error.
     */
    public abstract void execute() throws Exception;
}