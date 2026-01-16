package Controller;

public class ModifyNoteCommand extends Command {
    private int index;
    private String text;
    public ModifyNoteCommand(Controller controller, int index, String text) {
        super(controller);
        this.index = index;
        this.text = text;
    }
    public void execute() {
        controller.onModifyNoteClicked(this.index, this.text);
    }
}
