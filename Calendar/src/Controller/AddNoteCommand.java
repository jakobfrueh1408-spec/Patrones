package Controller;

public class AddNoteCommand extends Command {
    private String title;
    private String text;
    public AddNoteCommand(Controller controller, String title, String text) {
        super(controller);
        this.title = title;
        this.text = text;
    }
    public void execute() {
        controller.onAddNoteClicked(this.title, this.text);
    }

}
