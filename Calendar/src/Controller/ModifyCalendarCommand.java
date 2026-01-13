package Controller;

public class ModifyCalendarCommand extends Command {
    public ModifyCalendarCommand(Controller controller) {
        super(controller);
    }
    public void execute() {
        controller.onExitClicked();
    }
    public void fetchParameters() {

    }
}
