package Controller;

import Model.Calendar;
import Model.Label;

import java.util.Date;

public class AddEventCommand extends Command {
    private String title;
    private String description;
    private String label;
    private int lengthOfOccurrence;
    public AddEventCommand(Controller controller, String title, String description, String label, int lengthOfOccurrence) {
        super(controller);
        this.title = title;
        this.description = description;
        this.label = label;
        this.lengthOfOccurrence = lengthOfOccurrence;
    }
    public void execute() {
        controller.onAddEventClicked(this.title, this.description, this.label, this.lengthOfOccurrence);
    }
}
