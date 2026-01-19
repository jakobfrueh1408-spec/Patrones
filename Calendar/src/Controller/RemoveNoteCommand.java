package Controller;

/**
 * RemoveNoteCommand encapsulates the request to delete a specific note.
 * <p>
 * This concrete implementation of the Command pattern uses a positional index
 * to identify the note targeted for removal. When executed, it triggers the
 * deletion logic via the {@link Controller}, ensuring the day-view UI and
 * data model remain synchronized.
 * </p>
 */
public class RemoveNoteCommand extends Command {
    /** The index of the note in the daily list to be removed. */
    private int index;

    /**
     * Constructs a RemoveNoteCommand with the index of the note to be deleted.
     * * @param controller The {@link Controller} that will execute the removal logic.
     * @param index      The positional index of the note in the current view.
     */
    public RemoveNoteCommand(Controller controller, int index) {
        super(controller);
        this.index = index;
    }

    /**
     * Executes the removal process by invoking {@code onRemoveNoteClicked}
     * on the controller with the stored index.
     */
    @Override
    public void execute() {
        controller.onRemoveNoteClicked(this.index);
    }
}