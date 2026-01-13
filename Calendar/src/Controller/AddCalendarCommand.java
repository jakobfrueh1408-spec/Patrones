package Controller;

import Model.Season;

public class AddCalendarCommand extends Command {
    private String name;
    private int length;
    private Season start;
    public AddCalendarCommand(Controller controller, String name, int length, Season start) {
        super(controller);
        this.name = name;
        this.length = length;
        this.start = start;
    }
    public void execute() {
        controller.onAddCalendarClicked(this.name, this.length, this.start);
    }
    public void fetchParameters() {

    }
}
