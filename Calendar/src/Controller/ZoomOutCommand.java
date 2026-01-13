package Controller;

public class ZoomOutCommand extends Command {
    public ZoomOutCommand(Controller controller) {
        super(controller);
    }
    public void execute() {
        controller.onExitClicked();
    }
    public void fetchParameters() {

    }
}
