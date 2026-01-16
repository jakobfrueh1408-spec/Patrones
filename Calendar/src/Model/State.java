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
    public void zoomIn(int day, int month, int year){}
    public void addEvent(String  title, String description, String label, int lengthOfOccurrence){}
    public void removeEvent(String title){}
    public void modifyEvent(String title, String description){}
    public void addNote(String title,String text){}
    public void removeNote(String title){}
    public void modifyNote(String title, String description){}
    public void loadCalendar(String name){}
    public void removeCalendar(int indexToRemove){}
    public void modifyCalendar(String name, String newTitle){}

    //both SignedInState and EmptySignedInState
    public void addCalendar(String name,int length,  Season season,int year){}

    //only EmptySignedInState
    public void switchToSignedIn() {}

    //ZoomedInState
    public void zoomOut(){}

    //All states
    public void exit(){}

} 
