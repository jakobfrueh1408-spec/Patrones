package Model;

import java.util.Date;

public abstract class State {
    protected Model model;

    public State(Model model) {
        this.model = model;
    }
    public void setModel(Model model) {
        this.model = model;
    }

    //notSignedInState
    public void signIn(String name, String password){
        
    } 
    public void register(String name, String password, String birthday) {}

    //signedInState
    public void signOut(){}
    public void zoomIn(int indexToZoomIn, int dayToZoomIn){}
    public void addEvent(Event event, int boundary){}
    public void removeEvent(Event event){}
    public void modifyEvent(Event event){}
    public void addNote(Note note){}
    public void removeNote(Note note){}
    public void modifyNote(Note note){}
    public void modifyNote(Note note, String description){}
    public void modifyEvent(Event event, String description){}
    public void loadCalendar(Calendar calendar){}
    public void removeCalendar(int indexToRemove){}
    public void modifyCalendar(Calendar calendar, String newTitle){}

    //both SignedInState and EmptySignedInState
    public void addCalendar(int lenght, String name, Season season){}

    //only EmptySignedInState
    public void switchToSignedIn() {}

    //ZoomedInState
    public void zoomOut(){}

    //All states
    public void exit(){}

} 
