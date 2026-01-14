package View;

import Controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View {
    private Controller controller;
    private CalendarForm calendarForm;
    public View(Controller controller) {
        this.controller = controller;
        calendarForm = new CalendarForm(this);
    }
    public Controller getController() {
        return controller;
    }
    public CalendarForm getCalendarForm() {
        return this.calendarForm;
    }
}
