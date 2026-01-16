package Controller;

import Model.Season;

public class AddCalendarCommand extends Command {
    private String name;
    private int length;
    private String start;
    private int year;
    public AddCalendarCommand(Controller controller, String name, int length, String start, int year) {
        super(controller);
        this.name = name;
        this.length = length;
        this.start = start;
        this.year = year;
    }
    public void execute() {
        controller.onAddCalendarClicked(this.name, this.length, this.start, this.year);
    }
}
