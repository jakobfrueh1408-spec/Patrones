package Controller;

import Model.*;
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
    public void logoutClicked(){
        model.setState(new NotSignedIn(model));
    }
    public void onExitClicked(){
        System.exit(0);
    }
    public void loginClicked(){
        model.setState(new SignedIn(model));
    }

    public String[] getUserNames(){
        int numOfUsers = model.getUserPool().getUsers().size();
        String[] result = new String[numOfUsers];
        for(int i = 0; i < numOfUsers; i++){
            result[i] = model.getUserPool().getUsers().toString();
        }
        return result;
    }
}
