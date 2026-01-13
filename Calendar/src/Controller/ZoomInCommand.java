package Controller;

public class ZoomInCommand extends Command {
    public ZoomInCommand(Controller controller) {
        super(controller);
    }
    public void execute() {
        controller.onExitClicked();
    }
    public void fetchParameters() {

    }
}
