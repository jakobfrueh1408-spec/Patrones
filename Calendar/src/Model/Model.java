package Model;

import Controller.Controller;

import java.util.Date;

public class Model {
    private UserPool userPool;
    private User currentUser;
    private State state;
    private static Model instance;
    private Controller controller;

    private Model() {
        userPool = new UserPool();
        currentUser = null;
        this.controller = new Controller(this);
        state = new NotSignedIn(this);
    }

    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }

    //The functions which are needed for the implementations inside the states
    public UserPool getUserPool() {
        return userPool;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public State getState() {
        return state;
    }

    /*********************************************************************************************************************/
    // The functions which we can call indirectly using the model

    //notSignedInState
    public void signIn(String name, String password) {
        state.signIn(name, password);
    }

    public void register(String name, String password, String birthday) {
        state.register(name, password, birthday);
    }

    //signedInState
    public void signOut() {
        state.signOut();
    }

    public void zoomIn(int day, int month, int year) {
        //we need to set the current Date fo the current calendar of the current user
        // state function needs to det the day to zoom in
        state.zoomIn( int day, int month, int year);
    }

    public void loadCalendar(String name) {
        state.loadCalendar(name);
    }

    public void removeCalendar(int indexToRemove) {
        state.removeCalendar(indexToRemove);
    }

    public void modifyCalendar(String name, String title) {
        state.modifyCalendar(name, title);
    }

    //both SignedInState and EmptySignedInState
    public void addCalendar(String name, int length, Season season, int year) {
        state.addCalendar(name, length, season, year);
    }

    //ZoomedInState
    public void zoomOut() {
        state.zoomOut();
    }

    public void addEvent(String title, String description, String label, int lengthOfOccurrence) {
        //Problem with the Date persists
        state.addEvent(title, description, label, lengthOfOccurrence);
    }

    public void addNote(String title, String text) {
        state.addNote(title, text);
    }

    public void removeEvent(String title) {
        state.removeEvent(title);
    }

    public void removeNote(int index) {
        state.removeNote(index);
    }

    public void modifyNote(String title, String description) {
        state.modifyNote(title, description);
    }

    public void modifyEvent(String title, String description) {
        state.modifyEvent(title, description);
    }

    //all States
    public void exit() {
        state.exit();
    }
}