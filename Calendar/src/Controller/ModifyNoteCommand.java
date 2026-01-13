package Controller;

public class ModifyNoteCommand extends Command {
    public ModifyNoteCommand(Controller controller) {
        super(controller);
    }
    public void execute() {
        controller.onExitClicked();
    }
    public void fetchParameters() {

    }
}
