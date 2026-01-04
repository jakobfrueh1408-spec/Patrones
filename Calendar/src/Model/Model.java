package Model;

import java.util.Date;

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
    public void setController(Controller controller) {
        this.controller = controller;
    }
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    public State getState() {
        return state;
    }


    // The functions which we can call directly using the model

    //notSignedInState
    public void signIn(String name, String password){
        state.signIn(name, password);
    } 
    public void register(String name, String password, Date birthday, CalendarPool calendarPool ){
        state.register(name, password, null, calendarPool);
    } 

    //signedInState
    public void signOut(){
        state.signOut();
    }
    public void zoomIn(){
        state.zoomIn();
    }
    public void addEvent(Event event,int frequency, int boundary){
        state.addEvent(event,frequency,boundary);
    }
    public void addEvent(Event event){
        state.addEvent(event);
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
    //editing still has some problems (can we only edit the text or also the date and the kind of the event)
    public void editNote(Note note, String description){
        state.editNote(note, description);
    }
    public void editEvent(Event event, String description){
        state.editEvent(event, description);
    }
    public void loadCalendar(Calendar calendar){
        state.loadCalendar(calendar);
    }
    public void removeCalendar(Calendar calendar){
        state.removeCalendar(calendar);
    }
    //both SignedInState and EmptySignedInState
    public void addCalendar(int length, String name, Season season){
        state.addCalendar(length,name,season);
    }

    //only EmptySignedInState
    public void switchToSignedIn() {
        state.switchToSignedIn();
    }

    //ZoomedInState
    public void zoomOut(){
        state.zoomOut();
    }

    //all States
    public void exit(){
        state.exit();
    }
    public static void main( String[]args) {
        Model model = new Model(); 
        model.register("Kubo", "12345", new Date(2004,8,14), new CalendarPool());
        
        System.out.println(model.state);
        model.signIn("Kubo","12345"); 
        System.out.println(model.getCurrentUser());
        model.addCalendar(1,"Alcala",Season.Autumn);
        model.loadCalendar(model.getCurrentUser().getCalendars().getCalendar(0));
        System.out.println(model.state);
        model.addEvent(new Event("Football", "kickabout with the boys", new Date(2025,9,14), Label.sport), 1,14);
        model.addNote(new Note("sauna","Sauna with the retarded monkey", new Date(2025,11,20)));
        System.out.println(model.getCurrentUser().toString());
    }
    //Problem : Each state has completely different functions , no overlap , parent class does not declare a conjunction of common functions  
    // => need for casting everytime we want to call a stat function
    // also the day view only for visual purposes ? 
}
