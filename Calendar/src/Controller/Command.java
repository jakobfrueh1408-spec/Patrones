package Controller;

public abstract class Command {
    protected Controller controller;
    public Command(Controller controller) {
        this.controller = controller;
    }
    public abstract void execute();
    public abstract void fetchParameters();
}
