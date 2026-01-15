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
    public void signIn(String name, String password){
        state.signIn(name, password);
    }
    public void register(String name, String password, String birthday){
        state.register(name, password, birthday);
    }

    //signedInState
    public void signOut(){
        state.signOut();
    }
    public void zoomIn(int indexToZoomIn){
        state.zoomIn(indexToZoomIn);
    }
    public void addEvent(Event event, int boundary){
        state.addEvent(event, boundary);
    }
    public void addNote(Note note){
        state.addNote(note);
    }
    public void removeEvent(Event event){
        state.removeEvent(event);
    } 
    public void removeNote(Note note){
        state.removeNote(note);
    }
    public void modifyNote(Note note, String title, String description){
        state.modifyNote(note, description);
    }
    public void modifyEvent(Event event, String title, String description){
        state.modifyEvent(event, description);
    }
    public void loadCalendar(Calendar calendar){
        state.loadCalendar(calendar);
    }
    public void removeCalendar(int indexToRemove){
        state.removeCalendar(indexToRemove);
    }
    public void modifyCalendar(Calendar calendar, String title){state.modifyCalendar(calendar, title);}

    //both SignedInState and EmptySignedInState
    public void addCalendar(int length, String name, Season season){
        state.addCalendar(length,name,season);
    }

    //ZoomedInState
    public void zoomOut(){
        state.zoomOut();
    }

    //all States
    public void exit(){
        state.exit();
    }


    /*public static void main( String[]args) {
        //new Model();

        model.register("Kubo", "12345", new Date(2004,8,14), new CalendarPool());
        
        System.out.println(model.state);
        model.signIn("Kubo","12345"); 
        System.out.println(model.getCurrentUser());
        System.out.println(model.state);
        model.addCalendar(1,"Alcala",Season.Autumn);
        model.loadCalendar(model.getCurrentUser().getCalendars().getCalendar(0));
        
        model.addEvent(new Event("Football", "kickabout with the boys", new Date(2025 - 1900,7,14), Label.sport), 2);
        model.addNote(new Note("sauna","Sauna with the retarded monkey", new Date(2025 - 1900,11,20)));
        // System.out.println(model.getCurrentUser().toString());
        model.zoomIn();
        model.zoomOut();
        System.out.println(model.getCurrentUser());
        model.addCalendar(2, "Guadalajara", Season.Spring);
        System.out.println(model.getCurrentUser().getCalendars().getCalendars().size());
        model.loadCalendar(model.getCurrentUser().getCalendars().getCalendar(1));
        model.addEvent(new Event("Football", "kickabout with the boys", new Date(2025,7,14), Label.sport), 2);
        model.addNote(new Note("sauna","Sauna with the retarded monkey", new Date(2025,11,20)));
        System.out.println(model.getCurrentUser().toString());
        model.signOut();
        System.out.println(model.state);

    }*/
}
