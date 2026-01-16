package View;

import Model.Calendar;

import javax.swing.*;
import java.awt.*;

public class DayView extends JLabel {
    public DayView(Calendar calendar, int month, int day)
    {
        setLayout(new FlowLayout());
        for(int i = 0; i < numOfElements; i++){

            add(new JPanel());
        }
    }
}
