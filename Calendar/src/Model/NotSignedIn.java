package Model;

import java.util.ArrayList;

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
            User u = listToTraverse.get(i);
            //if the 
            System.out.println(u.getIdNumber() == u.hash(userName));
            if(u.getIdNumber().equals(u.hash(userName))  && u.getPassword().equals(password)) {
                model.setCurrentUser(u);
                break; 
            }
        }
        if(model.getCurrentUser() == null) {
            model.setState(new CreateCalendarState(model));
        } else {
            model.setState(new SignedIn(model));
        }

    }
    @Override
    public void register(String name, String password, String birthday){
        model.getUserPool().addUser(new User(name, password, birthday));
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
