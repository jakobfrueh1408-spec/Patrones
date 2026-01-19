package Controller;

import Model.Calendar;
import Model.Label;

import java.util.Date;

/**
 * AddEventCommand encapsulates the request to add a new event to a specific date.
 * It stores the event details and delegates the creation logic to the {@link Controller}.
 * <p>
 * By using the Command pattern, the application can handle event creation requests
 * uniformly, supporting a decoupled architecture between the View and the Model.
 * </p>
 */
public class AddEventCommand extends Command {
    private String title;
    private String description;
    private String label;
    private int lengthOfOccurrence;

    /**
     * Constructs an AddEventCommand with all necessary event details.
     * * @param controller         The {@link Controller} that will handle the logic.
     * @param title              The title of the event.
     * @param description        A detailed description of the event.
     * @param label              The category label (e.g., "study", "travel") as a String.
     * @param lengthOfOccurrence The number of weeks the event should repeat.
     */
    public AddEventCommand(Controller controller, String title, String description, String label, int lengthOfOccurrence) {
        super(controller);
        this.title = title;
        this.description = description;
        this.label = label;
        this.lengthOfOccurrence = lengthOfOccurrence;
    }

    /**
     * Executes the command by invoking the {@code onAddEventClicked} method
     * on the controller using the encapsulated event data.
     */
    @Override
    public void execute() {
        controller.onAddEventClicked(this.title, this.description, this.label, this.lengthOfOccurrence);
    }
}