package Controller;

import Model.*;
import View.*;

import javax.swing.*;
import java.awt.*;

public class Controller {
    private Model model;

    public void setView(View view) {
        this.view = view;
    }

    private View view;
    private JPanel contentPane;
    private CardLayout cardLayout;

    public Controller(Model model) {
        this.model = model;
    }
    public View getView() {
        return view;
    }

    //******************************************************** Main Menu State ******************************************************************//
    public void onExitClicked(){
        System.exit(0);
    }

    public void onSignInClicked(String name, String password) throws Exception {
        model.signIn(name, password);
        if(model.getCurrentUser().getCalendarPool().getCalendars() == null || model.getCurrentUser().getCalendarPool().getCalendars().isEmpty()){
            cardLayout.show(contentPane, "CreateCalendarPanel");
        } else {
            //if there is a calendar, we show it
            refreshCalendarView();
            cardLayout.show(contentPane, "SignedInPanel");
        }
    }

    public void onRegisterClicked(String name, String password,  String birthday){
        model.register(name, password, birthday);
    }
    public void onAddCalendarClicked(String name, int length, String start, int year){
        model.addCalendar(name,length, start, year);
        view.getCalendarForm().refreshCalendarList(model.getCurrentUser().getCalendarPool().getCalendars());
        System.out.println("-Controller- number of calendars: " + model.getCurrentUser().getCalendarPool().getCalendars().size());
        cardLayout.show(contentPane, "SignedInPanel");
    }


    //******************************************************** Signed In State ******************************************************************//
    public void onSignOutClicked(){
        model.signOut();
        cardLayout.show(contentPane, "MainMenuPanel");
    }

    public void onRemoveCalendarClicked(int indexToRemove){
        model.removeCalendar(indexToRemove);
        //if we delete the last calendar, we have to switch view state
        if(model.getCurrentUser().getCalendarPool().getCalendars().isEmpty()){
            cardLayout.show(contentPane, "CreateCalendarPanel");
        } else{
            Calendar updatedCalendar = model.getCurrentUser().getCalendarPool().getCalendars().get(0);
            model.loadCalendar(0);
            refreshCalendarView();

        }

        view.getCalendarForm().refreshCalendarList(model.getCurrentUser().getCalendarPool().getCalendars());
    }

    public void onModifyCalendarClicked(String newtitle){
        model.modifyCalendar(newtitle);
        view.getCalendarForm().refreshCalendarList(model.getCurrentUser().getCalendarPool().getCalendars());
        System.out.println(model.getCurrentUser().getCurrentCalendar().getName());
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
        model.addEvent(title, description, label, lengthOfOccurrence-1);
    }

    public void onRemoveEventClicked(int index){
        model.removeEvent(index);
    }

    public void onModifyEventClicked(int index, String text){
        model.modifyEvent(index, text);
    }

    public void onZoomOutClicked(){
        refreshCalendarView();
        model.zoomOut();
        cardLayout.show(contentPane, "SignedInPanel");


    }


    //******************************************************** Helping Functions ******************************************************************//
    public void refreshCalendarView(){
        view.getCalendarForm().setCurrentCalendar(model.getCurrentUser().getCurrentCalendar());
        view.getCalendarForm().refreshCalendarList(model.getCurrentUser().getCalendarPool().getCalendars());
        view.getCalendarForm().setDisplayedMonth(model.getCurrentUser().getCurrentCalendar().getCurrentDate());
        view.getCalendarForm().refreshYearMonthLabels();
        view.getCalendarForm().refreshEventsList(model.getCurrentUser().getCurrentCalendar().getCurrentDayEventList());
        view.getCalendarForm().refreshNotesList(model.getCurrentUser().getCurrentCalendar().getCurrentDayNoteList());
        view.getCalendarForm().repaintMonthView(model.getCurrentUser().getCurrentCalendar());
    }

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
        return model.getCurrentUser().getCalendarPool().getCalendars().get(index);
    }

    public void setCurrentCalendar(Calendar calendar){
        model.getCurrentUser().setCurrentCalendar(calendar);
    }

    public void setContentPane(JPanel contentPane) {
        this.contentPane = contentPane;
    }

    public void setCardLayout(CardLayout cardLayout) {
        this.cardLayout = cardLayout;
    }

}
