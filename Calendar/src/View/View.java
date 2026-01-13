package View;

import Controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View {
    private Controller controller;
    private CalendarFrame calendarFrame;
    public View(Controller controller) {
        this.controller = controller;
        CalendarFrame calendarFrame = new CalendarFrame(this);
    }
    public Controller getController() {
        return controller;
    }
    public CalendarFrame getCalendarFrame() {
        return calendarFrame;
    }
}
