package Controller;

public class RemoveNoteCommand extends Command {
    public RemoveNoteCommand(Controller controller) {
        super(controller);
    }
    public void execute() {
        controller.onExitClicked();
    }
    public void fetchParameters() {

    }
}
