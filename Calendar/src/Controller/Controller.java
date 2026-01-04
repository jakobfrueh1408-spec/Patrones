package Controller;

import Model.Model;
import View.*;

public class Controller {
    private Model model;
    private View view;
    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }
    public Controller() {
        this.model = Model.getInstance();
        this.model.setController(this);
        this.view = new View(this);
    }
    public void onLogOutClicked(){

    }
    public void onExitClicked(){
        System.exit(0);
    }
}
