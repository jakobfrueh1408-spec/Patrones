package Model;

import Database.CalendarTableManager;
import Database.DatabaseDAO;
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
        if(loggedUser != null){
            model.setCurrentUser(loggedUser);
            model.setState(new SignedIn(model, new CalendarTableManager()));
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
    public String toString(){
        return "NotSignedIn";
    }
}
