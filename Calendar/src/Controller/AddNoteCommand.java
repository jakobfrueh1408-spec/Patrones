package Controller;

/**
 * AddNoteCommand encapsulates the request to create a new personal note.
 * It stores the title and content text, delivering them to the {@link Controller}
 * when executed.
 * <p>
 * This class is a concrete implementation of the Command pattern, facilitating
 * a decoupled interaction between the day-view interface and the application model.
 * </p>
 */
public class AddNoteCommand extends Command {
    /** The heading or summary of the note. */
    private String title;

    /** The detailed body text of the note. */
    private String text;

    /**
     * Constructs an AddNoteCommand with the necessary note details.
     * * @param controller The {@link Controller} that will process the request.
     * @param title      The title of the note.
     * @param text       The main body text of the note.
     */
    public AddNoteCommand(Controller controller, String title, String text) {
        super(controller);
        this.title = title;
        this.text = text;
    }

    /**
     * Executes the command by invoking the {@code onAddNoteClicked} method
     * on the controller with the stored title and text.
     */
    @Override
    public void execute() {
        controller.onAddNoteClicked(this.title, this.text);
    }
}