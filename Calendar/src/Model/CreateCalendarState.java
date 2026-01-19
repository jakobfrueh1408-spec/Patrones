package Model;

import Database.CalendarTableManager;
import Database.DatabaseDAO;

public class CreateCalendarState extends State{
    

    public CreateCalendarState(Model model, DatabaseDAO database){
        super(model,database);
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
