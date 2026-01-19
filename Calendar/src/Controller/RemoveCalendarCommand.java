package Controller;

/**
 * RemoveCalendarCommand encapsulates the request to delete an existing calendar.
 * <p>
 * This concrete implementation of the Command pattern stores the index of the
 * calendar to be removed. When executed, it triggers the removal logic in
 * the {@link Controller}, which updates both the persistence layer and the
 * runtime {@link Model.CalendarPool}.
 * </p>
 */
public class RemoveCalendarCommand extends Command {
    /** The index of the calendar in the user's list that is targeted for removal. */
    private int indexToRemove;

    /**
     * Constructs a RemoveCalendarCommand with the specified calendar index.
     * * @param controller The {@link Controller} that will process the deletion logic.
     * @param index      The positional index of the calendar to be removed.
     */
    public RemoveCalendarCommand(Controller controller, int index) {
        super(controller);
        this.indexToRemove = index;
    }

    /**
     * Executes the removal process by invoking {@code onRemoveCalendarClicked}
     * on the controller with the stored index.
     */
    @Override
    public void execute() {
        controller.onRemoveCalendarClicked(indexToRemove);
    }
}