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

    //******************************************************** Main Menu State ******************************************************************//
    public void onExitClicked(){
        System.exit(0);
    }

    public void onSignInClicked(String name, String password){
        model.signIn(name, password);
        if(model.getCurrentUser() == null || model.getCurrentUser().getCalendars().getCalendars().isEmpty()){
            cardLayout.show(contentPane, "CreateCalendarPanel");
        } else {
            //if there is a calendar, we show it
            view.getCalendarForm().repaintMonthView(model.getCurrentUser().getCurrentCalendar());
            cardLayout.show(contentPane, "SignedInPanel");
        }
    }

    public void onRegisterClicked(String name, String password,  String birthday){
        model.register(name, password, birthday);
    }
    public void onAddCalendarClicked(String name, int length, String start, int year){
        //if we were in the emptysignedin state, we have to switch view state
        if(model.getCurrentUser().getCalendars().getCalendars().isEmpty())
            cardLayout.show(contentPane, "SignedInPanel");
        model.addCalendar(name,length, start, year);
    }


    //******************************************************** Signed In State ******************************************************************//
    public void onSignOutClicked(){
        model.signOut();
        cardLayout.show(contentPane, "MainMenuPanel");
    }

    public void onRemoveCalendarClicked(int indexToRemove){
        model.removeCalendar(indexToRemove);
        //if we delete the last calendar, we have to switch view state
        if(model.getCurrentUser().getCalendars().getCalendars().isEmpty())
            cardLayout.show(contentPane, "CreateCalendarPanel");
    }

    public void onModifyCalendarClicked(String newtitle){
        model.modifyCalendar(newtitle);
    }

    public void onZoomInClicked(int year, int month, int day){
        model.zoomIn(year, month, day);
        cardLayout.show(contentPane, "ZoomedInPanel");
    }


    //******************************************************** Zoomed In State ******************************************************************//
    public void onAddNoteClicked(String title, String text){
        model.addNote(title, text);
    }

    public void onRemoveNoteClicked(int index){
        model.removeNote(index);
    }

    public void onModifyNoteClicked(int index, String text){
        model.modifyNote(index, text);
    }

    public void onAddEventClicked(String  title, String description, String label, int lengthOfOccurrence){
        model.addEvent(title, description, label, lengthOfOccurrence);
    }

    public void onRemoveEventClicked(int index){
        model.removeEvent(index);
    }

    public void onModifyEventClicked(int index, String text){
        model.modifyEvent(index, text);
    }

    public void onZoomOutClicked(){
        model.zoomOut();
        cardLayout.show(contentPane, "SignedInPanel");
    }


    //******************************************************** Helping Functions ******************************************************************//
    public String[] getUserNames(){
        int numOfUsers = model.getUserPool().getUsers().size();
        String[] result = new String[numOfUsers];
        for(int i = 0; i < numOfUsers; i++){
            result[i] = model.getUserPool().getUsers().toString();
        }
        return result;
    }

    public Calendar getCurrentCalendar(){
        return model.getCurrentUser().getCurrentCalendar();
    }

    public Calendar getCalendar(int index){
        return model.getCurrentUser().getCalendars().getCalendars().get(index);
    }
}
