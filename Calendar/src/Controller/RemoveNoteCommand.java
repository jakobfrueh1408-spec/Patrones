package Controller;

public class RemoveNoteCommand extends Command {
    private int index;
    public RemoveNoteCommand(Controller controller, int index) {
        super(controller);
        this.index = index;
    }
    public void execute() {
        controller.onRemoveNoteClicked(this.index);
    }
}
