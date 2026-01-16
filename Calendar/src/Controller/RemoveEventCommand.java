package Controller;

public class RemoveEventCommand extends Command {
    private int index;
    public RemoveEventCommand(Controller controller, int index) {
        super(controller);
        this.index = index;
    }
    public void execute() {
        controller.onRemoveEventClicked(this.index);
    }
}
