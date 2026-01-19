package Model;

import Controller.ModelObserver;
import Database.UserTableManager;

import java.util.ArrayList;

public class Model {
    private UserPool userPool;
    private User currentUser;
    private State state;
    private static Model instance;
    private final ArrayList<ModelObserver> observers = new ArrayList<>();

    private Model() {
        userPool = new UserPool();
        currentUser = null;
        state = new NotSignedIn(this, new UserTableManager());
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
        notifyObservers();
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public String getState() {
        return state.toString();
    }

    /*********************************************************************************************************************/

    //notSignedInState
    public void signIn(String name, String password) throws Exception {
        state.signIn(name, password);
    }

    public void register(String name, String password, String birthday) {
        state.register(name, password, birthday);
    }

    //signedInState
    public void signOut() {
        state.signOut();
    }

    public void zoomIn(int year, int month, int day) {
        state.zoomIn(year, month, day);
    }


    public void removeCalendar(int indexToRemove) {
        state.removeCalendar(indexToRemove);
        notifyObservers();
    }

    public void modifyCalendar( String title) {
        state.modifyCalendar( title);
        notifyObservers();
    }

    //CreateCalendarState
    public void addCalendar(String name, int length, String season, int year) {
        state.addCalendar(name, length, season, year);
    }

    //ZoomedInState
    public void zoomOut() {
        state.zoomOut();
    }

    public void addEvent(String title, String description, String label, int lengthOfOccurrence) {
        state.addEvent(title, description, label, lengthOfOccurrence);
        notifyObservers();
    }

    public void switchToCreateCalendar(){
        state.switchToCreateCalendar();
    }

    public void addNote(String title, String text) {
        state.addNote(title, text);
        notifyObservers();
    }

    public void removeEvent(int indexToRemove) {
        state.removeEvent(indexToRemove);
        notifyObservers();
    }

    public void removeNote(int indexToRemove) {
        state.removeNote(indexToRemove);
        notifyObservers();
    }

    public void modifyNote(int indexToRemove, String description) {
        state.modifyNote(indexToRemove, description);
        notifyObservers();
    }

    public void modifyEvent(int indexToModify, String description) {
        state.modifyEvent(indexToModify, description);
        notifyObservers();
    }

    //all States
    public void exit() {
        state.exit();
    }

    public void addObserver(ModelObserver observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        for (ModelObserver observer : observers) {
            observer.modelChanged();
        }
    }
}