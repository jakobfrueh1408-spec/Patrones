package Application;

import Controller.Controller;
import Model.Model;
import View.View;

public class ErasmusCalendar {
    public static void startCalendar(){
        Model model = Model.getInstance();
        Controller controller = new Controller(model);
        View view = new View(controller, model);
    }
}
