package Controller;

public class SwitchToCalendarCreationCommand extends Command {
    public SwitchToCalendarCreationCommand(Controller controller) {
        super(controller);
    }
    public void execute() {
        controller.onCreateNewCalendarClicked();
    }
}
