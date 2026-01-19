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
    public void signIn(String name, String password) throws Exception {
        state.signIn(name, password);
    }

    public void register(String name, String password, String birthday) {
        state.register(name, password, birthday);
        System.out.println("registrated: " + name + " " + password + " " + birthday);
    }

    //signedInState
    public void signOut() {
        state.signOut();
    }

    public void zoomIn(int year, int month, int day) {
        //we need to set the current Date fo the current calendar of the current user
        // state function needs to det the day to zoom in
        state.zoomIn(year, month, day);
    }

    public void loadCalendar(int indexToLoad) {
        state.loadCalendar(indexToLoad);
    }

    public void removeCalendar(int indexToRemove) {
        state.removeCalendar(indexToRemove);
    }

    public void modifyCalendar( String title) {
        state.modifyCalendar( title);
    }

    //both SignedInState and EmptySignedInState
    public void addCalendar(String name, int length, String season, int year) {
        state.addCalendar(name, length, season, year);
        //System.out.println("-Model- added: " + name + " " + length + " " + season + " " + year);
        //System.out.println("-Model- NUMBER OF CALENDARS: " + getCurrentUser().getCalendarPool().getCalendars().size());
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

    public void removeEvent(int indexToRemove) {
        state.removeEvent(indexToRemove);
    }

    public void removeNote(int indexToRemove) {
        state.removeNote(indexToRemove);
    }

    public void modifyNote(int indexToRemove, String description) {
        state.modifyNote(indexToRemove, description);
    }

    public void modifyEvent(int indexToModify, String description) {
        state.modifyEvent(indexToModify, description);
    }

    //all States
    public void exit() {
        state.exit();
    }

    public void addObserver(ModelObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ModelObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (ModelObserver observer : observers) {
            observer.modelChanged();
        }
    }
}