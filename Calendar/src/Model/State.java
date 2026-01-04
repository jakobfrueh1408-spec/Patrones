package Model;

import java.sql.Date;

public abstract class State {
    protected Model model;
    public State(Model model) {
        this.model = model;
    }

    public void setModel(Model model) {
        this.model = model;
    
    }

    //notSignedInState
    public void signIn(String name, String password){} 
    public void register(String name, String password, Date birthday, CalendarPool calendarPool ){} 

    //signedInState
    public void signOut(){}
    public void zoomIn(){}
    public void addEvent(Event event,int frequency, int boundary){}
    public void addEvent(Event event){}
    public void addNote(Note note){}
    public void removeEvent(Event event){} 
    public void removeNote(Note note){}
    //editing still has some problems (can we only edit the text or also the date and the kind of the event)
    public void editNote(Note note, String description){}
    public void editEvent(Event event, String description){}
    public void loadCalendar(Calendar calendar){}
    public void removeCalendar(Calendar calendar){}
    //both SignedInState and EmptySignedInState
    public void addCalendar(int lenght, String name, Season season){}

    //only EmptySignedInState
    public void switchToSignedIn() {}

    //ZoomedInState
    public void zoomOut(){}

    //all States
    public void exit(){}
} 
