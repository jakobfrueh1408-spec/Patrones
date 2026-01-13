package Controller;

public class SignInCommand extends Command {
    private String userName;
    private String password;
    public SignInCommand(Controller controller) {
        super(controller);
        userName = "";
        password = "";
    }
    public void fetchParameters() {
        //String newName = this.controller.getView().getCalendarFrame()
        //String newPassword = this.controller.getView().getCalendarFrame()
        this.userName = "newName";
        this.password = "newPassword";
    }
    public void execute() {
        controller.onSignInClicked(this.userName, this.password);
    }
}