package Controller;

import Model.*;
import Model.Event;
import Model.Label;
import View.*;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class Controller {
    private Model model;
    private View view;
    private JPanel contentPane;
    private CardLayout cardLayout;
    public Controller(Model model) {
        this.model = model;
        this.view = new View(this);
        this.cardLayout = view.getCalendarForm().getCardLayout();
        this.contentPane = view.getCalendarForm().getContentPane();
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
        if(model.getCurrentUser() == null || model.getCurrentUser().getCalendars().getCalendars().isEmpty()){
            cardLayout.show(contentPane, "EmptySignedInPanel");
        } else {
            cardLayout.show(contentPane, "SignedInPanel");
        }
    }
    public void onRegisterClicked(String name, String password,  String birthday){
        model.register(name, password, birthday);
    }
    public void onAddCalendarClicked(String name, int length, Season start){
        //if we were in the emptysignedin state, we have to switch view state
        if(model.getCurrentUser().getCalendars().getCalendars().isEmpty())
            cardLayout.show(contentPane, "SignedInPanel");
        model.addCalendar(length, name, start);
    }
    public void onRemoveCalendarClicked(int indexToRemove){
        model.removeCalendar(indexToRemove);
        //if we delete the last calendar, we have to switch view state
        if(model.getCurrentUser().getCalendars().getCalendars().isEmpty())
            cardLayout.show(contentPane, "EmptySignedInPanel");
    }
    public void onModifyCalendarClicked(){

    }
    public void onSignOutClicked(){
        model.signOut();
        cardLayout.show(contentPane, "MainMenuPanel");
    }
    public void onZoomInClicked(int indexToZoomIn, int dayToZoomIn){
        model.zoomIn(indexToZoomIn, dayToZoomIn);
        cardLayout.show(contentPane, "ZoomedInPanel");
    }
    public void onAddNoteClicked(){

    }
    public void onRemoveNoteClicked(){

    }
    public void onModifyNoteClicked(){

    }
    public void onAddEventClicked(String  title, String description, String label, int lengthOfOccurrence){
        model.addEvent(title, description, label, lengthOfOccurrence);
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
