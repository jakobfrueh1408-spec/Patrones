package Model;

import Database.CalendarTableManager;
import Database.DatabaseDAO;

public class CreateCalendarState extends State{
    

    public CreateCalendarState(Model model, DatabaseDAO database){
        super(model,database);
    }

    //signedInState
    @Override
    public void signOut(){}
    @Override
    public void zoomIn(int year, int month, int day){}
    @Override
    public void addEvent(String  title, String description, String label, int lengthOfOccurrence){}
    @Override
    public void addNote(String title,String text){}
    @Override
    public void removeEvent(int indexToRemove){}
    @Override 
    public void removeNote(int indexToRemove){}

    //editing still has some problems (can we only edit the text or also the date and the kind of the event)
    @Override
    public void modifyNote(int indexToModify, String description){}
    @Override
    public void modifyEvent(int indexToModify, String description){}
    
    @Override
    public void removeCalendar(int indexToRemove){
        model.getCurrentUser().getCalendarPool().removeCalendar(indexToRemove);
    }


    @Override
    public void addCalendar( String name,int length, String season,int year){

        model.getCurrentUser().createCalendar(name,length,season,year);
        switchToSignedIn();
    }
    @Override
    public void switchToSignedIn() {
        int numOfCals = model.getCurrentUser().getCalendarPool().getCalendars().size();
        if(numOfCals == 1){
            model.getCurrentUser().setCurrentCalendar(model.getCurrentUser().getCalendarPool().getCalendars().get(0));
        }
        model.setState(new SignedIn(model,new CalendarTableManager()));
    }

    @Override
    public void loadCalendar(int indexToLoad){
        Calendar calendarToAdd ;
        calendarToAdd = model.getCurrentUser().getCalendarPool().getCalendarByIndex(indexToLoad);

        if(calendarToAdd != null){
            model.getCurrentUser().setCurrentCalendar(calendarToAdd);
        }

    }
    

    //ZoomedInState
    @Override
    public void zoomOut(){}

    //all States
    @Override
    public void exit(){
        //TO DO DB
        ;
    }

    public String toString(){
        return "CreateCalendar";
    }
}
