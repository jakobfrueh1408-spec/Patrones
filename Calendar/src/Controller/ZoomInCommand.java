package Controller;

public class ZoomInCommand extends Command {
    private int index;
    private int dayToZoomIn;
    public ZoomInCommand(Controller controller, int indexToZoomIn, int dayToZoomIn) {
        super(controller);
        this.index = indexToZoomIn;
        this.dayToZoomIn = dayToZoomIn;
    }
    public void execute() {
        controller.onZoomInClicked(this.index, this.dayToZoomIn);
    }
}
