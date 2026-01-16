package View;

import Model.Calendar;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DayView extends JLabel {
    public DayView(Calendar calendar, int month, int day)
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        for(int i = 0; i < calendar.getEvents().size(); i++){
            String title = calendar.getEvents().get(i).getTitle();
            String text = calendar.getEvents().get(i).getDescription();
            JLabel titlelabel = new JLabel(title);
            titlelabel.setVerticalAlignment(JLabel.NORTH);
            JLabel textlabel = new JLabel(text);
            textlabel.setVerticalAlignment(JLabel.SOUTH);
            add(titlelabel);
            add(textlabel);
        }
        for(int i = 0; i < calendar.getNotes().size(); i++){
            String title = calendar.getNotes().get(i).getTitle();
            String text = calendar.getNotes().get(i).getText();
            JLabel titlelabel = new JLabel(title);
            titlelabel.setVerticalAlignment(JLabel.NORTH);
            JLabel textlabel = new JLabel(text);
            textlabel.setVerticalAlignment(JLabel.SOUTH);
            add(titlelabel);
            add(textlabel);
        }
    }
}
