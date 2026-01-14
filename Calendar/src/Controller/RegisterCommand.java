package Controller;

public class RegisterCommand extends Command {
    public RegisterCommand(Controller controller) {
        super(controller);
    }
    public void execute() {
        controller.onRegisterClicked();
    }
    public void fetchParameters() {
    }
}
