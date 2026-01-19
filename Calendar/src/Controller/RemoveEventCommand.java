package Controller;

/**
 * RemoveEventCommand encapsulates the request to delete an existing event
 * from the daily view.
 * <p>
 * Following the Command Design Pattern, this class stores the index of the
 * event to be removed and executes the logic by calling the appropriate
 * handler in the {@link Controller}. This ensures the View remains decoupled
 * from the data-removal logic.
 * </p>
 */
public class RemoveEventCommand extends Command {
    /** The index of the event in the day's event list to be removed. */
    private int index;

    /**
     * Constructs a RemoveEventCommand with a reference to the controller
     * and the target index.
     * * @param controller The {@link Controller} that manages the removal process.
     * @param index      The positional index of the event in the current list.
     */
    public RemoveEventCommand(Controller controller, int index) {
        super(controller);
        this.index = index;
    }

    /**
     * Executes the command by invoking {@code onRemoveEventClicked}
     * on the controller with the encapsulated index.
     */
    @Override
    public void execute() {
        controller.onRemoveEventClicked(this.index);
    }
}