package Model;

import Database.CalendarTableManager;
import Database.DatabaseDAO;
import Database.EventNoteTableManager;
import Database.UserTableManager;

import java.util.ArrayList;

public class NotSignedIn extends State <UserTableManager>{
    public NotSignedIn(Model model, UserTableManager database) {
        super(model,database);
    }

    @Override
    public void signIn(String userName, String password) throws Exception {
        //set the current user in the model

        ArrayList <User> listToTraverse = model.getUserPool().getUsers();
        System.out.println("number of users: " + listToTraverse.size());

        //add database operation
        User loggedUser = getDatabase().getUserByLogin(userName, password);
        CalendarTableManager calendarTableManager = new CalendarTableManager();
        EventNoteTableManager eventNoteTableManager = new EventNoteTableManager();
        ArrayList<Calendar>  userCalendars = calendarTableManager.loadAllCalendarsForUser(loggedUser.getIdNumber());

        System.out.println(loggedUser);
        System.out.println(userCalendars);
        if(loggedUser != null){
            model.setCurrentUser(loggedUser);
            model.getCurrentUser().getCalendarPool().setCalendars(userCalendars);

            int numOfUserCals = model.getCurrentUser().getCalendarPool().getCalendars().size();
            if(numOfUserCals ==0){
                model.setState(new CreateCalendarState(model, new CalendarTableManager()));
            }else{
                model.getCurrentUser().setCurrentCalendar(userCalendars.get(0));
                ArrayList <Event> eventCalendarList = eventNoteTableManager.fetchEvents(model.getCurrentUser().getCurrentCalendar().getDb_id());
                ArrayList <Note> noteCalendarList = eventNoteTableManager.fetchNotes(model.getCurrentUser().getCurrentCalendar().getDb_id());
                System.out.println("This is the eventCalendar: "+eventCalendarList+"\n");
                System.out.println("This is the notecalendar:"+noteCalendarList+"\n");
                model.getCurrentUser().getCurrentCalendar().setEvents(eventCalendarList);
                model.getCurrentUser().getCurrentCalendar().setNotes(noteCalendarList);
                model.setState(new SignedIn(model, new CalendarTableManager()));
            }

        }else {
            throw new Exception("Not registered user!");
        }
    }

    @Override
    public void register(String name, String password, String birthday){
        UserTableManager usertable = new UserTableManager();
        User user = new User(name,password,birthday);
        usertable.register(user);
        model.getUserPool().addUser(user);
        System.out.println("Users in the userpool"+model.getUserPool());
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
    public void loadCalendar(int indexToLoad){}
    @Override
    public void removeCalendar(int indexToRemove){}
    @Override
    public void addCalendar( String name,int length, String season,int year){}
    

    //ZoomedInState
    @Override
    public void zoomOut(){}

    //all States
    @Override
    public void exit(){
        model.exit();
    }

    @Override
    public String toString(){
        return "NotSignedIn";
    }
}
