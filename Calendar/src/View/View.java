package View;

import Controller.Controller;
import Controller.ModelObserver;
import Model.*;

import javax.swing.*;

public class View implements ModelObserver {
    private Controller controller;
    private CalendarForm calendarForm;

    public View(Controller controller) {
        this.controller = controller;
        controller.getModel().addObserver(this);
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
        if (controller.getModel().getCurrentUser() == null) {
            calendarForm.showMainMenuPanel();
            return;
        }
        Calendar current = controller.getModel().getCurrentUser().getCurrentCalendar();
        if (current == null) {
            calendarForm.showCreateCalendarPanel();
            return;
        }

        calendarForm.setCurrentCalendar(current);
        calendarForm.refreshCalendarList(controller.getModel().getCurrentUser().getCalendarPool().getCalendars());
        calendarForm.setDisplayedMonth(current.getCurrentDate());
        calendarForm.refreshYearMonthLabels();
        calendarForm.refreshDayPanel();
        calendarForm.refreshEventsList(current.getCurrentDayEventList());
        calendarForm.refreshNotesList(current.getCurrentDayNoteList());
        calendarForm.repaintMonthView();

        switch(controller.getModel().getState()){
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

}
