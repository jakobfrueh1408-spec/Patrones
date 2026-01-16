package Controller;

public class ZoomInCommand extends Command {
    private int year;
    private int month;
    private int day;
    public ZoomInCommand(Controller controller, int year, int month, int day) {
        super(controller);
        this.year = year;
        this.month = month;
        this.day = day;
    }
    public void execute() {
        controller.onZoomInClicked(this.year, this.month, this.day);
    }
}
