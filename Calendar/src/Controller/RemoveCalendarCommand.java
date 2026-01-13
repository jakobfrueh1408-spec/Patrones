package Controller;

public class RemoveCalendarCommand extends Command {
    public RemoveCalendarCommand(Controller controller) {
        super(controller);
    }
    public void execute() {
        controller.onExitClicked();
    }
    public void fetchParameters() {

    }
}
