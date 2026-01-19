package Controller;

/**
 * SignInCommand encapsulates the user credentials required to authenticate
 * and access the application.
 * <p>
 * This class implements the Command Design Pattern to handle the sign-in
 * process. It captures the username and password from the login view and
 * passes them to the {@link Controller} for verification against the database.
 * </p>
 */
public class SignInCommand extends Command {
    /** The username provided by the user during the login attempt. */
    private String userName;

    /** The password provided by the user during the login attempt. */
    private String password;

    /**
     * Constructs a SignInCommand with the provided credentials.
     * * @param controller The {@link Controller} that will handle the authentication logic.
     * @param userName   The username entered in the login form.
     * @param password   The password entered in the login form.
     */
    public SignInCommand(Controller controller, String userName, String password) {
        super(controller);
        this.userName = userName;
        this.password = password;
    }

    /**
     * Executes the sign-in process by invoking {@code onSignInClicked}
     * on the controller.
     * * @throws Exception if authentication fails or if there is a connection
     * error with the database.
     */
    @Override
    public void execute() throws Exception {
        controller.onSignInClicked(this.userName, this.password);
    }
}