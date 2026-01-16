package Controller;

public class ModifyEventCommand extends Command {
    private int index;
    private String text;
    public ModifyEventCommand(Controller controller, int index, String text) {
        super(controller);
        this.index = index;
        this.text = text;
    }
    public void execute() {
        controller.onModifyEventClicked(this.index, this.text);
    }
}
