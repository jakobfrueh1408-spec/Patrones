package Model;

import java.util.Date;

public class SignedIn extends State{
    public SignedIn(Model model) {
        super(model);
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
        model.setState(new NotSignedIn(model));
    }
   @Override
    public void addCalendar(String name, int length,  Season season, int year){
        model.getCurrentUser().createCalendar( name,  length,  season, year);
    }

    @Override
    public void zoomIn(int day, int month, int year){

        //using the information passed by the controller to set the new currentDate
        Date date = Calendar.dateCreator(day,month,year);
        model.getCurrentUser().getCurrentCalendar().setCurrentDate(date);

        //setting the new state
        model.setState(new ZoomedInState(model));
    }
   
    //emptySignedInState and SignedInstate
    @Override
    public void loadCalendar(int indexToLoad){
        Calendar calendarToAdd ;
        calendarToAdd = model.getCurrentUser().getCalendars().getCalendarByIndex(indexToLoad);
        if(calendarToAdd != null){
            model.getCurrentUser().setCurrentCalendar(calendarToAdd);
        }
    }
    @Override
    public void removeCalendar(int indexToRemove){
        //removing the calendar by index
        model.getCurrentUser().getCalendars().removeCalendar(indexToRemove);
        // checking the number of calendars in the calendarpool
        int numOfCals = model.getCurrentUser().getCalendars().getCalendars().size();

        if(numOfCals ==0) {
            model.setState( new CreateCalendarState(model));
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