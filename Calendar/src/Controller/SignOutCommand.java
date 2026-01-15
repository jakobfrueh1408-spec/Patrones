package Controller;

public class SignOutCommand extends Command {
    public SignOutCommand(Controller controller) {
        super(controller);
    }
    public void execute() {
        controller.onSignOutClicked();
    }
}
