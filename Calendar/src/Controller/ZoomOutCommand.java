package Controller;

/**
 * ZoomOutCommand encapsulates the request to return from a detailed daily view
 * back to the broader calendar overview.
 * <p>
 * This command is typically triggered by a "Back" or "Return" button within
 * the zoomed-in interface. It initiates a state transition in the {@link Model},
 * effectively moving the application from the {@link Model.ZoomedInState} back
 * to the {@link Model.SignedIn} state.
 * </p>
 */
public class ZoomOutCommand extends Command {

    /**
     * Constructs a ZoomOutCommand.
     * @param controller The {@link Controller} responsible for handling navigation logic.
     */
    public ZoomOutCommand(Controller controller) {
        super(controller);
    }

    /**
     * Executes the zoom-out operation by invoking {@code onZoomOutClicked()}
     * on the controller. This triggers the transition to the parent view level
     * in the application's state machine.
     */
    @Override
    public void execute() {
        controller.onZoomOutClicked();
    }
}