package Controller;

public class ModifyCalendarCommand extends Command {
    private String newtitle;
    public ModifyCalendarCommand(Controller controller, String newtittle) {
        super(controller);
        this.newtitle = newtittle;
    }
    public void execute() {
        controller.onModifyCalendarClicked(this.newtitle);
    }
}
