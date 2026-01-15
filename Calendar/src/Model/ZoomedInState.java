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
    public void register(String name, String password, String  birthday){}

    //signedInState
    @Override
    public void signOut(){}
    @Override
    public void addEvent(Event event, int bounadry){}
    @Override
    public void addNote(Note note){}
    @Override
    public void removeEvent(Event event){}
    @Override 
    public void removeNote(Note note){}
    @Override
    public void modifyEvent(Event event,String description){}
    @Override
    public void modifyNote(Note note,String description){}

    @Override
    public void loadCalendar(Calendar calendar){}
    @Override
    public void removeCalendar(int indexToRemove){}
    @Override
    public void zoomIn(int indexToZoomIn, int dayToZoomIn){}
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
