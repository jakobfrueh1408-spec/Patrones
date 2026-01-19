package Controller;

import Model.*;
import View.*;

import javax.swing.*;
import java.awt.*;

public class Controller {
    private Model model;
    private View view;
    private JPanel contentPane;
    private CardLayout cardLayout;

    public Controller(Model model) {
        this.model = model;
    }

    //******************************************************** Main Menu State ******************************************************************//
    public void onExitClicked(){
        System.exit(0);
    }

    public void onSignInClicked(String name, String password) throws Exception {
        model.signIn(name, password);
    }

    public void onRegisterClicked(String name, String password,  String birthday){
        model.register(name, password, birthday);
    }

    public void onAddCalendarClicked(String name, int length, String start, int year){
        model.addCalendar(name,length, start, year);
    }


    //******************************************************** Signed In State ******************************************************************//
    public void onSignOutClicked(){
        model.signOut();
    }

    public void onRemoveCalendarClicked(int indexToRemove){
        model.removeCalendar(indexToRemove);
    }

    public void onModifyCalendarClicked(String newtitle){
        model.modifyCalendar(newtitle);
    }

    public void onZoomInClicked(int year, int month, int day){
        model.zoomIn(year, month, day);
    }

    public void onCreateNewCalendarClicked(){
        model.switchToCreateCalendar();
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
        model.zoomOut();
    }


    //******************************************************** Helping Functions ******************************************************************//

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

    public void setView(View view) {
        this.view = view;
    }

    public Model getModel(){
        return model;
    }
}
