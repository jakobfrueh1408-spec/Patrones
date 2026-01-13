package Controller;

public class AddNoteCommand extends Command {
    public AddNoteCommand(Controller controller) {
        super(controller);
    }
    public void execute() {
        controller.onExitClicked();
    }
    public void fetchParameters() {

    }
}
