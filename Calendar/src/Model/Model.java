package Model;

import Controller.*;

public class Model {
    private UserPool userPool;
    private User currentUser;
    private Controller controller;
    private State state;
    private static Model instance;
    private Model() {
        userPool = new UserPool();
        currentUser = null;
        state = new NotSignedIn(this);
    }
    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }
    public UserPool getUserPool() {
        return userPool;
    }
    public void setState(State state) {
        this.state = state;
    }
    public void exit(){
        ;
    }
    public void setController(Controller controller) {
        this.controller = controller;
    }
}
