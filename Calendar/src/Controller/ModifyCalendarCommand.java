package Controller;

/**
 * ModifyCalendarCommand encapsulates the request to update the properties
 * (specifically the title) of an existing calendar.
 * <p>
 * This concrete implementation of the Command pattern allows the UI to
 * trigger renaming operations while maintaining a decoupled architecture
 * between the View and the Controller logic.
 * </p>
 */
public class ModifyCalendarCommand extends Command {
    /** The new title to be assigned to the calendar. */
    private String newtitle;

    /**
     * Constructs a ModifyCalendarCommand with the new title information.
     * * @param controller The {@link Controller} that will process the modification.
     * @param newtittle  The new name/title for the calendar.
     */
    public ModifyCalendarCommand(Controller controller, String newtittle) {
        super(controller);
        this.newtitle = newtittle;
    }

    /**
     * Executes the command by delegating the title update to the
     * controller's {@code onModifyCalendarClicked} method.
     */
    @Override
    public void execute() {
        controller.onModifyCalendarClicked(this.newtitle);
    }
}