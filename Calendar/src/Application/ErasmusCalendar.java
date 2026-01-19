package Application;

import Controller.Controller;
import Model.Model;
import View.View;

/**
 * The ErasmusCalendar class serves as the entry point for the application.
 * It is responsible for initializing the Model-View-Controller (MVC) layers
 * and establishing the initial connections between them to launch the calendar.
 */
public class ErasmusCalendar {

    /**
     * Initializes the application's core architecture.
     * This method retrieves the singleton instance of the {@link Model},
     * creates a {@link Controller} to manage that model, and finally
     * instantiates the {@link View} to display the user interface.
     */
    public static void startCalendar(){
        Model model = Model.getInstance();
        Controller controller = new Controller(model);
        View view = new View(controller);
    }
}