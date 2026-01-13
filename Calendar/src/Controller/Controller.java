package Controller;

import Model.*;
import View.*;

import java.util.Date;

public class Controller {
    private Model model;
    private View view;
    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public Controller() {
        this.model = Model.getInstance();
        this.view = new View(this);
    }
    public View getView() {
        return view;
    }
    /***********************************************************/

    public void onExitClicked(){
        System.exit(0);
    }
    public void onSignInClicked(String name, String password){
        model.signIn(name, password);
    }
    public void onRegisterClicked(){

    }
    public void onAddCalendarClicked(String name, int length, Season start){
        model.addCalendar(length, name, start);
    }
    public void onRemoveCalendarClicked(){

    }
    public void onModifyCalendarClicked(){

    }
    public void onSignOutClicked(){
        model.setState(new NotSignedIn(model));
    }
    public void onZoomInClicked(){

    }
    public void onAddNoteClicked(){

    }
    public void onRemoveNoteClicked(){

    }
    public void onModifyNoteClicked(){

    }
    public void onAddEventClicked(String  title, String description, Label label, int lengthOfOccurrence, Date date){
        Event eventToAdd = new Event(title, description, date, label);
        model.getCurrentUser().getCurrentCalendar().addEvent(eventToAdd, lengthOfOccurrence);
    }
    public void onRemoveEventClicked(){

    }
    public void onModifyEventClicked(){

    }
    public void onZoomOutClicked(){

    }



    public String[] getUserNames(){
        int numOfUsers = model.getUserPool().getUsers().size();
        String[] result = new String[numOfUsers];
        for(int i = 0; i < numOfUsers; i++){
            result[i] = model.getUserPool().getUsers().toString();
        }
        return result;
    }
}
