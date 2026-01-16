package Model;

import Database.CalendarTableManager;
import Database.EventNoteTableManager;
import Database.UserTableManager;

import java.util.Date;

public class SignedIn extends State<CalendarTableManager>{
    public SignedIn(Model model, CalendarTableManager database) {
        super(model,database);
    }
    
    //notSignedInState
    @Override
    public void signIn(String name , String password){}
    @Override 
    public void register(String name, String password, String birthday){}

    //zoomedInState
    @Override
    public void addEvent(String  title, String description, String label, int lengthOfOccurrence){}
    @Override
    public void addNote(String title,String text){}
    @Override
    public void removeEvent(int indexToRemove){}
    @Override
    public void removeNote(int indexToRemove){}
    @Override
    public void modifyEvent(int indexToModify,String description){}
    @Override
    public void modifyNote(int indexToModify,String description){}

    //signedInState
    @Override
    public void signOut(){
        model.setCurrentUser(null);
        model.setState(new NotSignedIn(model,new UserTableManager()));
    }
   @Override
    public void addCalendar(String name, int length,  String season, int year){
        model.getCurrentUser().createCalendar( name,  length,  season, year);
    }

    @Override
    public void zoomIn(int day, int month, int year){

        //using the information passed by the controller to set the new currentDate
        Date date = Calendar.dateCreator(day,month,year);
        model.getCurrentUser().getCurrentCalendar().setCurrentDate(date);

        //setting the new state
        model.setState(new ZoomedInState(model, new EventNoteTableManager()));
    }
   
    //emptySignedInState and SignedInstate
    @Override
    public void loadCalendar(int indexToLoad){
        Calendar calendarToAdd ;
        calendarToAdd = model.getCurrentUser().getCalendarPool().getCalendarByIndex(indexToLoad);
        if(calendarToAdd != null){
            model.getCurrentUser().setCurrentCalendar(calendarToAdd);
        }
    }
    @Override
    public void removeCalendar(int indexToRemove){
        //removing the calendar by index
        model.getCurrentUser().getCalendarPool().removeCalendar(indexToRemove);
        // checking the number of calendars in the calendarpool
        int numOfCals = model.getCurrentUser().getCalendarPool().getCalendars().size();

        if(numOfCals ==0) {
            model.setState( new CreateCalendarState(model,new CalendarTableManager()));
        }
    }
    @Override
    public void modifyCalendar(String name){
        model.getCurrentUser().getCurrentCalendar().setName(name);
    }

    @Override
    public void switchToSignedIn() {}

    @Override
    public void exit(){
        model.exit();
    }
}