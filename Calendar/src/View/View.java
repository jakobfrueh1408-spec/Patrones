package View;

import Controller.Controller;
import Controller.ModelObserver;
import Model.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View implements ModelObserver {
    private Controller controller;
    private Model model;
    private CalendarForm calendarForm;

    public View(Controller controller, Model model) {
        this.controller = controller;
        this.model = model;
        model.addObserver(this);
        calendarForm = new CalendarForm(this);
        controller.setCardLayout(calendarForm.getCardLayout());
        controller.setContentPane(calendarForm.getContentPane());
        controller.setView(this);
    }


    @Override
    public void modelChanged() {
        this.refreshView();
    }

    private void refreshView(){
        if (model.getCurrentUser() == null) {
            System.out.println("State-" + model.getState() + "-Vége");
            calendarForm.showMainMenuPanel();
            return;
        }
        Calendar current = model.getCurrentUser().getCurrentCalendar();
        if (current == null) {
            System.out.println("State-" + model.getState() + "-Vége");
            calendarForm.showCreateCalendarPanel();
            return;
        }

        System.out.println("State-" + model.getState() + "-Vége");

        calendarForm.setCurrentCalendar(current);
        calendarForm.refreshCalendarList(model.getCurrentUser().getCalendarPool().getCalendars());
        calendarForm.setDisplayedMonth(current.getCurrentDate());
        calendarForm.refreshYearMonthLabels();
        calendarForm.refreshDayPanel();
        calendarForm.refreshEventsList(current.getCurrentDayEventList());
        calendarForm.refreshNotesList(current.getCurrentDayNoteList());
        calendarForm.repaintMonthView(current);

        switch(model.getState()){
            case "MainMenu":
                calendarForm.showMainMenuPanel();
                break;
            case "CreateCalendar":
                calendarForm.showCreateCalendarPanel();
                break;
            case "SignedIn":
                calendarForm.showSignedInPanel();
                break;
            case "ZoomedIn":
                calendarForm.showZoomedInPanel();
                break;
            default:
                calendarForm.showMainMenuPanel();
        }

    }

    public Controller getController() {
        return controller;
    }

    public CalendarForm getCalendarForm() {
        return this.calendarForm;
    }

}
