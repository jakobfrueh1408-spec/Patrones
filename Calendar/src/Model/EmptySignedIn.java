package Model;

public class EmptySignedIn extends State{
    

    public EmptySignedIn(Model model){
        super(model);
    }

    //signedInState
    @Override
    public void signOut(){}
    @Override
    public void zoomIn(){}
    @Override
    public void addEvent(Event event,int frequency, int boundary){}
    @Override
    public void addEvent(Event event){}
    @Override
    public void addNote(Note note){}
    @Override
    public void removeEvent(Event event){}
    @Override 
    public void removeNote(Note note){}

    //editing still has some problems (can we only edit the text or also the date and the kind of the event)
    @Override
    public void editNote(Note note, String description){}
    @Override
    public void editEvent(Event event, String description){}
    
    @Override
    public void removeCalendar(Calendar calendar){}


    @Override
    public void addCalendar(int lenght, String name, Season season){
        model.getCurrentUser().createCalendar(lenght,name,season);
    }
    @Override
    public void switchToSignedIn() {
        model.setState(new SignedIn(model));
    }

    @Override
    public void loadCalendar(Calendar calendar){
        model.getCurrentUser().setCurrentCalendar(calendar);
        switchToSignedIn();
    }
    

    //ZoomedInState
    @Override
    public void zoomOut(){}

    //all States
    @Override
    public void exit(){
        model.exit();
    }
}
