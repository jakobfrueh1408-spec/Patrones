package Model;

public class CreateCalendarState extends State{
    

    public CreateCalendarState(Model model){
        super(model);
    }

    //signedInState
    @Override
    public void signOut(){}
    @Override
    public void zoomIn(int day, int month, int year){}
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
        model.getCurrentUser().getCalendars().removeCalendar(indexToRemove);
    }


    @Override
    public void addCalendar( String name,int length, Season season,int year){
        model.getCurrentUser().createCalendar(name,length,season,year);
    }
    @Override
    public void switchToSignedIn() {
        model.setState(new SignedIn(model));
    }

    @Override
    public void loadCalendar(int indexToLoad){
        Calendar calendarToAdd ;
        calendarToAdd = model.getCurrentUser().getCalendars().getCalendarByIndex(indexToLoad);
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
        return "EmptySignedIn";
    }
}
