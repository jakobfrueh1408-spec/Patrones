package Model;

import java.util.ArrayList;
import java.util.Date;

public class NotSignedIn extends State{
    public NotSignedIn(Model model) {
        super(model);
    }
    @Override
    public void signIn(String userName, String password){
        //set the current user in the model
       
        ArrayList <User> listToTraverse = model.getUserPool().getUsers();
        System.out.println(listToTraverse.size());
        for(int i = 0; i < listToTraverse.size(); i++) {
            System.out.println("halllo");
            User u = listToTraverse.get(i);
            //if the 
            System.out.println(u.getIdNumber() == u.hash(userName));
            if(u.getIdNumber().equals(u.hash(userName))  && u.getPassword().equals(password)) {
                model.setCurrentUser(u);
                System.out.println("halllo");
                break; 
            }
        }
        if(model.getCurrentUser() == null) {
            model.setState(new EmptySignedIn(model));
        } else {
            model.setState(new SignedIn(model));
        }

    }
    @Override
    public void register(String name, String password, Date birthday, CalendarPool calendarPool ){
        model.getUserPool().addUser(new User(name, password, birthday,calendarPool));
        System.out.println("Users in the userpool"+model.getUserPool());
    } 
    //signedInState
    @Override
    public void signOut(){}
    @Override
    public void zoomIn(){}
    @Override
    public void addEvent(Event event, int boundary){}
    @Override
    public void addNote(Note note){}
    @Override
    public void removeEvent(Event event){}
    @Override 
    public void removeNote(Note note){}

    //editing still has some problems (can we only edit the text or also the date and the kind of the event)
    @Override
    public void modifyNote(Note note, String description){}
    @Override
    public void modifyEvent(Event event, String description){}
    @Override
    public void loadCalendar(Calendar calendar){}
    @Override
    public void removeCalendar(Calendar calendar){}
    @Override
    public void addCalendar(int lenght, String name, Season season){}
    

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
