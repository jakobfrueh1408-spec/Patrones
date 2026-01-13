package Controller;

public class RegisterCommand extends Command {
    public RegisterCommand(Controller controller) {
        super(controller);
    }
    public void execute() {
        controller.onExitClicked();
    }
    public void fetchParameters() {

    }
}
