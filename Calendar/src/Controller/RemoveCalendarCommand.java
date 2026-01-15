package Controller;

public class RemoveCalendarCommand extends Command {
    private int indexToRemove;
    public RemoveCalendarCommand(Controller controller, int index) {
        super(controller);
        this.indexToRemove = index;
    }
    public void execute() {
        controller.onRemoveCalendarClicked(indexToRemove);
    }
}
