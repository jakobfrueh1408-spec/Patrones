package Controller;

/**
 * ZoomInCommand encapsulates the request to view a specific date in detail.
 * <p>
 * This command stores the date coordinates (year, month, and day) and triggers
 * the transition from the general calendar view to the daily "Zoomed In" view.
 * This follows the Command Design Pattern to decouple UI interactions from the
 * state-switching logic in the {@link Controller}.
 * </p>
 */
public class ZoomInCommand extends Command {
    /** The year of the date to be focused on. */
    private int year;

    /** The month of the date to be focused on (typically 0-11 or 1-12 depending on model logic). */
    private int month;

    /** The specific day of the month to be focused on. */
    private int day;

    /**
     * Constructs a ZoomInCommand with the target date coordinates.
     * * @param controller The {@link Controller} that will handle the navigation logic.
     * @param year       The year of the selected date.
     * @param month      The month of the selected date.
     * @param day        The day of the selected date.
     */
    public ZoomInCommand(Controller controller, int year, int month, int day) {
        super(controller);
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Executes the zoom-in operation by invoking {@code onZoomInClicked} on the
     * controller with the encapsulated date parameters. This typically results
     * in the application changing its state to {@link Model.ZoomedInState}.
     */
    @Override
    public void execute() {
        controller.onZoomInClicked(this.year, this.month, this.day);
    }
}