package Model;

import Database.DatabaseDAO;

import java.util.Date;

public abstract class State<T extends DatabaseDAO> {
    protected Model model;
    private T database;

    public State(Model model, T database) {
        this.database = database;
        this.model = model;
    }

    //getters and setters
    public void setModel(Model model) {
        this.model = model;
    }
    public T getDatabase() {
        return database;
    }
    public void setDatabase(T database) {
        this.database = database;
    }

    //notSignedInState
    public void signIn(String name, String password) throws Exception {
        
    } 
    public void register(String name, String password, String birthday) {}

    //signedInState
    public void signOut(){}
    public void zoomIn(int day, int month, int year){}
    public void removeCalendar(int indexToRemove){}
    public void modifyCalendar( String newTitle){}

    //both SignedInState and CreateCalendarState
    public void addCalendar(String name,int length,  String season,int year){}
    public void loadCalendar(int indexToLoad){}

    //only CreateCalendarState
    public void switchToSignedIn() {}

    //ZoomedInState
    public void zoomOut(){}
    public void addEvent(String  title, String description, String label, int lengthOfOccurrence){}
    public void removeEvent(int indexToRemove){}
    public void modifyEvent(int indexToRemove, String description){}
    public void addNote(String  title, String text){}
    public void removeNote(int indexToRemove){}
    public void modifyNote(int indexToModify, String description){}

    //All states
    public void exit(){}

} 
