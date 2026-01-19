package Controller;

/**
 * ModifyEventCommand encapsulates the request to update or edit an existing event.
 * <p>
 * This command stores the index of the event within the current view's list and
 * the new text/description to be applied. It follows the Command Design Pattern
 * to decouple the user interface from the logic of event modification.
 * </p>
 */
public class ModifyEventCommand extends Command {
    /** The positional index of the event in the current day's event list. */
    private int index;

    /** The new description or title text to be updated in the event. */
    private String text;

    /**
     * Constructs a ModifyEventCommand with the necessary identification and update data.
     * * @param controller The {@link Controller} that will execute the business logic.
     * @param index      The index of the event to be modified.
     * @param text       The new content for the event.
     */
    public ModifyEventCommand(Controller controller, int index, String text) {
        super(controller);
        this.index = index;
        this.text = text;
    }

    /**
     * Executes the modification by calling {@code onModifyEventClicked} on the controller
     * with the encapsulated index and text parameters.
     */
    @Override
    public void execute() {
        controller.onModifyEventClicked(this.index, this.text);
    }
}