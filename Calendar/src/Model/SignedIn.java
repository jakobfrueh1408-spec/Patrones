package Model;

import Database.CalendarTableManager;
import Database.EventNoteTableManager;
import Database.UserTableManager;

import java.time.LocalDate;
import java.util.Date;

public class SignedIn extends State<CalendarTableManager>{
    public SignedIn(Model model, CalendarTableManager database) {
        super(model,database);
    }

    //signedInState
    @Override
    public void signOut(){
        model.setCurrentUser(null);
        model.setState(new NotSignedIn(model,new UserTableManager()));
    }

    @Override
    public void zoomIn(int year, int month, int day){

        //using the information passed by the controller to set the new currentDate
        LocalDate date = LocalDate.of(year, month, day);
        model.getCurrentUser().getCurrentCalendar().setCurrentDate(date);

        //setting the new state
        model.setState(new ZoomedInState(model, new EventNoteTableManager()));
    }

    @Override
    public void switchToCreateCalendar(){
        System.out.println("HALOOOOOOOO");
        model.setState(new CreateCalendarState(model, new CalendarTableManager()));
    }

    @Override
    public void removeCalendar(int indexToRemove){
        //removing the calendar by index
        int removal_id = getCalendarIdByName(indexToRemove);
        System.out.printf("Trying to delete id : %d",removal_id);
        getDatabase().removeCalendar(removal_id);
        model.getCurrentUser().getCalendarPool().removeCalendar(indexToRemove);
        // checking the number of calendars in the calendarpool
        int numOfCals = model.getCurrentUser().getCalendarPool().getCalendars().size();
        if(numOfCals ==0) {
            model.setState( new CreateCalendarState(model,new CalendarTableManager()));
        }
    }
    @Override
    public void modifyCalendar(String name){
        Calendar cal = model.getCurrentUser().getCurrentCalendar();
        int indx =model.getCurrentUser().getCalendarPool().getCalendars().indexOf(cal);
        int modifyId= getCalendarIdByName(indx);
        getDatabase().modifyCalendar(modifyId,name);
        model.getCurrentUser().getCurrentCalendar().setName(name);
    }

    @Override
    public void exit(){
        model.exit();
    }

    public int getCalendarIdByName( int indx){
        String nameOfRemoval =model.getCurrentUser().getCalendarPool().getCalendars().get(indx).getName();
        String userId = model.getCurrentUser().getIdNumber();
        Calendar cal = getDatabase().getCalendarByName(nameOfRemoval,userId);
        int cal_id = cal.getDb_id();
        return cal_id;
    }

    @Override
    public String toString() {
        return "SignedIn";
    }
}