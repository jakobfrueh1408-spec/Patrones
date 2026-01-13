package Controller;

public class RemoveEventCommand extends Command {
    public RemoveEventCommand(Controller controller) {
        super(controller);
    }
    public void execute() {
        controller.onExitClicked();
    }
    public void fetchParameters() {

    }
}
