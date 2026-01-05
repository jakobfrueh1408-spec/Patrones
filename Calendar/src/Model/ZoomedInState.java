package Model;

import java.util.Date;

public class ZoomedInState extends State {
   
    public ZoomedInState(Model model){
        super(model);
    }
    
    @Override
    public void zoomOut(){
        model.setState(new SignedIn(model));
    }

    //notSignedInState
    @Override
    public void signIn(String name, String password){}
    @Override 
    public void register(String name, String password,Date birthday, CalendarPool calendarPool ){} 

    //signedInState
    @Override
    public void signOut(){}
    @Override
    public void addEvent(Event event){}
    @Override
    public void addNote(Note note){}
    @Override
    public void removeEvent(Event event){}
    @Override 
    public void removeNote(Note note){}
    @Override
    public void editNote(Note note,String description){}
    @Override
    public void editEvent(Event event,String description){}
    @Override
    public void loadCalendar(Calendar calendar){}
    @Override
    public void removeCalendar(Calendar calendar){}
    @Override
    public void zoomIn(){}
    @Override
    public void addCalendar(int lenght, String name, Season season){}
    @Override
    public void switchToSignedIn() {}
    //all States
    @Override
    public void exit(){
        model.exit();
    }
}
