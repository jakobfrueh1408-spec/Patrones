package Controller;

import Model.Season;

/**
 * AddCalendarCommand encapsulates the request to create a new calendar.
 * It stores the necessary parameters (name, length, season, year) and executes
 * the action by calling the corresponding method in the {@link Controller}.
 * <p>
 * This follows the Command Design Pattern, allowing the UI to trigger a
 * calendar addition without being tightly coupled to the Controller's logic.
 * </p>
 */
public class AddCalendarCommand extends Command {
    private String name;
    private int length;
    private String start;
    private int year;

    /**
     * Constructs an AddCalendarCommand with the specified parameters.
     * * @param controller The {@link Controller} that will handle the logic.
     * @param name       The title of the new calendar.
     * @param length     The duration of the calendar in semesters.
     * @param start      The starting season (e.g., "Autumn" or "Spring").
     * @param year       The starting year of the calendar.
     */
    public AddCalendarCommand(Controller controller, String name, int length, String start, int year) {
        super(controller);
        this.name = name;
        this.length = length;
        this.start = start;
        this.year = year;
    }

    /**
     * Executes the command by delegating the call to the controller's
     * {@code onAddCalendarClicked} method with the stored parameters.
     */
    @Override
    public void execute() {
        controller.onAddCalendarClicked(this.name, this.length, this.start, this.year);
    }
}