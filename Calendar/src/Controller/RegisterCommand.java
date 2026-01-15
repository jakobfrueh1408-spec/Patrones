package Controller;

import java.util.Date;

public class RegisterCommand extends Command {
    private String userName;
    private String password;
    private String birthday;
    public RegisterCommand(Controller controller,  String username, String password,  String birthday) {
        super(controller);
        this.userName = username;
        this.password = password;
        this.birthday = birthday;
    }
    public void execute() {
        controller.onRegisterClicked(userName, password,  birthday);
    }
}
