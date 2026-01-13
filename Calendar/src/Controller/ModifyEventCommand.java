package Controller;

public class ModifyEventCommand extends Command {
    public ModifyEventCommand(Controller controller) {
        super(controller);
    }
    public void execute() {
        controller.onExitClicked();
    }
    public void fetchParameters() {

    }
}
