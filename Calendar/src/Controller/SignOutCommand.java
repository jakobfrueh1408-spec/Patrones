package Controller;

/**
 * SignOutCommand encapsulates the request to log the current user out of the application.
 * <p>
 * This command triggers the teardown of the current user session in the {@link Model}.
 * Following the Command pattern, it allows the logout action to be initiated from
 * various UI components (like a sidebar button or a menu item) without coupling
 * the View to the specific session-clearing logic.
 * </p>
 */
public class SignOutCommand extends Command {

    /**
     * Constructs a SignOutCommand.
     * @param controller The {@link Controller} instance that manages the logout logic.
     */
    public SignOutCommand(Controller controller) {
        super(controller);
    }

    /**
     * Executes the sign-out process by calling {@code onSignOutClicked()}
     * on the controller. This typically resets the current user to null and
     * redirects the UI back to the Main Menu.
     */
    @Override
    public void execute() {
        controller.onSignOutClicked();
    }
}