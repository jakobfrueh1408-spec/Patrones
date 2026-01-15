package Controller;

public class ZoomInCommand extends Command {
    private int index;
    public ZoomInCommand(Controller controller, int indexToZoomIn) {
        super(controller);
        this.index = indexToZoomIn;
    }
    public void execute() {
        controller.onZoomInClicked(this.index);
    }
}
