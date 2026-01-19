package Model;

import Database.CalendarTableManager;
import Database.DatabaseDAO;
import Database.EventNoteTableManager;

import java.util.Date;

public class ZoomedInState extends State <EventNoteTableManager>{
   
    public ZoomedInState(Model model,EventNoteTableManager database){
        super(model,database);
    }


    /**
     NOT SIGNED IN STATE
     */
    @Override
    public void signIn(String name, String password){}
    @Override 
    public void register(String name, String password, String  birthday){}

    /**
     SIGNED IN STATE
     */
    @Override
    public void signOut(){}
    @Override
    public void loadCalendar(int indexToLoad){}
    @Override
    public void removeCalendar(int indexToRemove){}
    @Override
    public void zoomIn(int day, int month, int year){}
    @Override
    public void addCalendar( String name,int length, String season,int year){}
    @Override
    public void switchToSignedIn() {}
    //all States
    @Override
    public void exit(){
        model.exit();
    }


    /**
    ZOOMED IN STATE
     */


    //adding Events and Notes
    @Override
    public void addEvent(String  title, String description, String label, int lengthOfOccurrence){
        // check if there is a current calendar to be manipulated
        if(model.getCurrentUser().getCurrentCalendar() == null) {
            //throw some Exception or something ( until now just return)
            return;
        }
        model.getCurrentUser().getCurrentCalendar().addEvent(title,description,label,lengthOfOccurrence);
    }
    @Override
    public void addNote(String title,String text){
        // check if there is a current calendar to be manipulated
        if(model.getCurrentUser().getCurrentCalendar() == null) {
            //throw some Exception or something ( until now just return)
            return;
        }
        model.getCurrentUser().getCurrentCalendar().addNote(title,text);
    }

    //removing Events and Notes
    @Override
    public void removeEvent(int indexToRemove){
        model.getCurrentUser().getCurrentCalendar().removeEvent(indexToRemove);
    }
    @Override
    public void removeNote(int indexToRemove){
        model.getCurrentUser().getCurrentCalendar().removeNote(indexToRemove);
    }

    //modifying Events and Notes
    @Override
    public void modifyNote(int indexToModify, String description){
        //still every unsure about that until now only editing texts, we need to overload
        model.getCurrentUser().getCurrentCalendar().modifyNote(indexToModify, description);

    }
    @Override
    public void modifyEvent(int indexToModify, String description){
        //still every unsure about that until now only editing texts
        model.getCurrentUser().getCurrentCalendar().modifyEvent(indexToModify, description);
    }

    @Override
    public void zoomOut(){
        // make the current Date in the Current Calenadar in the Current User null, idk if that is good practice with the null though
        //model.getCurrentUser().getCurrentCalendar().setCurrentDate(null);
        model.setState(new SignedIn(model,new CalendarTableManager()));
    }

    @Override
    public String toString() {
        return "ZoomedIn";
    }
}
