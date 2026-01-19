package Controller;

import java.util.Date;

/**
 * RegisterCommand encapsulates the request to create a new user account.
 * <p>
 * This command stores the registration details provided by the user and
 * triggers the registration logic in the {@link Controller} when executed.
 * It ensures that the sensitive process of account creation is decoupled
 * from the specific UI implementation.
 * </p>
 */
public class RegisterCommand extends Command {
    /** The desired username for the new account. */
    private String userName;

    /** The password for the new account. */
    private String password;

    /** The birth date of the user, typically used for profile details. */
    private String birthday;

    /**
     * Constructs a RegisterCommand with all necessary user profile data.
     * * @param controller The {@link Controller} that will handle the registration logic.
     * @param username   The username entered in the registration form.
     * @param password   The password entered in the registration form.
     * @param birthday   The birthday string entered in the registration form.
     */
    public RegisterCommand(Controller controller, String username, String password, String birthday) {
        super(controller);
        this.userName = username;
        this.password = password;
        this.birthday = birthday;
    }

    /**
     * Executes the registration process by invoking the {@code onRegisterClicked}
     * method on the controller with the stored user details.
     */
    @Override
    public void execute() {
        controller.onRegisterClicked(userName, password, birthday);
    }
}