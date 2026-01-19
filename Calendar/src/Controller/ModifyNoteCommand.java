package Controller;

/**
 * ModifyNoteCommand encapsulates the request to update the content of an existing note.
 * <p>
 * This concrete implementation of the Command pattern stores the positional index
 * of the note and the updated text. It ensures that the user interface remains
 * decoupled from the specific logic required to update the note in the Model.
 * </p>
 */
public class ModifyNoteCommand extends Command {
    /** The index of the note within the current day's note collection. */
    private int index;

    /** The new text content to be saved into the note. */
    private String text;

    /**
     * Constructs a ModifyNoteCommand with the necessary update data.
     * * @param controller The {@link Controller} instance that will execute the request.
     * @param index      The unique index used to identify the note to be modified.
     * @param text       The new body text for the note.
     */
    public ModifyNoteCommand(Controller controller, int index, String text) {
        super(controller);
        this.index = index;
        this.text = text;
    }

    /**
     * Executes the note modification by delegating the update to the
     * controller's {@code onModifyNoteClicked} method.
     */
    @Override
    public void execute() {
        controller.onModifyNoteClicked(this.index, this.text);
    }
}