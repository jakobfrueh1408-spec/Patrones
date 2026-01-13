package Controller;

import Model.Calendar;
import Model.Label;

import java.util.Date;

public class AddEventCommand extends Command {
    private String title;
    private String description;
    private Label label;
    private int lengthOfOccurrence;
    private Date date;
    public AddEventCommand(Controller controller, Calendar calendar, String title, String description, Label label, int lengthOfOccurrence, Date date) {
        super(controller);
        this.title = title;
        this.description = description;
        this.label = label;
        this.lengthOfOccurrence = lengthOfOccurrence;
        this.date = date;
    }
    public void execute() {
        controller.onAddEventClicked(this.title, this.description, this.label, this.lengthOfOccurrence, this.date);
    }
    public void fetchParameters() {

    }
}
