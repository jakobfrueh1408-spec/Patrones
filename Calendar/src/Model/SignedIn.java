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
    public void register(String name, String password, Date birthday, CalendarPool calendarPool){} 

    //signedInState
    @Override
    public void signOut(){
        model.setCurrentUser(null);
        model.setState(new NotSignedIn(model));
    }
   @Override
    public void addCalendar(int lenght, String name, Season season){
        model.getCurrentUser().createCalendar(lenght,name,season);
    }
  
    @Override
    public void addEvent(Event event, int boundary){
            // check if there is a current calendar to be manipulated
        if(model.getCurrentUser().getCurrentCalendar() == null) {
            //throw some Exception or something ( until now just return)
            return; 
        }
        model.getCurrentUser().getCurrentCalendar().addEvent(event,boundary);
    }
    @Override
    public void addNote(Note note){
        // check if there is a current calendar to be manipulated
        if(model.getCurrentUser().getCurrentCalendar() == null) {
            //throw some Exception or something ( until now just return)
            return; 
        }
        model.getCurrentUser().getCurrentCalendar().addNote(note); 
    }
    @Override
    public void removeEvent(Event event){
        model.getCurrentUser().getCurrentCalendar().removeEvent(event);
    }
    @Override 
    public void removeNote(Note note){
        model.getCurrentUser().getCurrentCalendar().removeNote(note);
    }
    @Override
    public void modifyNote(Note note, String description){
        //still every unsure about that until now only editing texts, we need to overload
        model.getCurrentUser().getCurrentCalendar().modifyNote(note, description);
    
    }
    @Override
    public void modifyEvent(Event event, String description){
        //still every unsure about that until now only editing texts
        model.getCurrentUser().getCurrentCalendar().modifyEvent(event, description);
    }
    

    @Override
    public void zoomIn(){
        model.setState(new ZoomedInState(model));
    }
   
    //emptySignedInState and SignedInstate
    @Override
    public void loadCalendar(Calendar calendar){
        model.getCurrentUser().setCurrentCalendar(calendar);
    }
    @Override
    public void removeCalendar(Calendar calendar){
        model.getCurrentUser().getCalendars().removeCalendar(calendar);
    }
    @Override
    public void modifyCalendar(Calendar calendar, String newName ){
        model.getCurrentUser().getCurrentCalendar().setName(newName);
    }

    @Override
    public void switchToSignedIn() {}

    @Override
    public void exit(){
        model.exit();
    }
}