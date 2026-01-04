package View;

import Controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View {

    public View(Controller controller) {
        CalendarFrame calendarFrame = new CalendarFrame(controller);
    }
}
