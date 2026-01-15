package Controller;

public class ExitCommand extends Command {
    public ExitCommand(Controller controller) {
        super(controller);
    }
    public void execute() {
        controller.onExitClicked();
    }
}
