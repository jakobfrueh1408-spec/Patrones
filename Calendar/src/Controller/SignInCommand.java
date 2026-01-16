package Controller;

public class SignInCommand extends Command {
    private String userName;
    private String password;
    public SignInCommand(Controller controller, String userName, String password) {
        super(controller);
        this.userName = userName;
        this.password = password;
    }
    public void execute() throws Exception {
        controller.onSignInClicked(this.userName, this.password);
    }
}