package View;

import Model.Calendar;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MonthView extends JPanel {

    private final int year;
    private final int month;
    private Calendar calendar;

    public MonthView(int month, Calendar calendar) {
        int year = calendar.getYear();
        this.calendar = calendar;
        if((calendar.getSeason().toString() == "Autumn" && month >= 5) || (calendar.getSeason().toString() == "Spring" && month >= 10)){
            year++;
        }
        this.year = year;
        this.month = month;
        //System.out.println(month);

        setLayout(new GridLayout(0, 7));
        buildMonth();
    }

    private void buildMonth() {
        removeAll();

        for (DayOfWeek day : DayOfWeek.values()) {
            JLabel header = new JLabel(
                    day.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                    SwingConstants.CENTER
            );
            header.setFont(header.getFont().deriveFont(Font.BOLD));
            add(header);
        }

        LocalDate firstDay = LocalDate.of(year, month, 1);
        int firstDayColumn = firstDay.getDayOfWeek().getValue() % 7;

        for (int i = 0; i < firstDayColumn; i++) {
            add(createEmptyCell());
        }

        int daysInMonth = firstDay.lengthOfMonth();
        for (int day = 1; day <= daysInMonth; day++) {
            add(createDayCell(day, calendar.dayEventTitles(day), calendar.dayLabelTitles(day), calendar.dayNoteTitles(day)));
        }

        revalidate();
        repaint();
    }


    private JPanel createDayCell(int day, ArrayList<String> eventTitles, ArrayList<String> labelTitles, ArrayList<String> noteTitles) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new LineBorder(Color.LIGHT_GRAY));

        JLabel label = new JLabel(String.valueOf(day));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 4));

        panel.add(label, BorderLayout.NORTH);

        for(int i = 0 ; i < eventTitles.size(); i++) {
            JLabel title = new JLabel(eventTitles.get(i));
            switch(labelTitles.get(i)){
                case "sport":{
                    title.setBackground(Color.RED);
                    break;
                }
                case "study":{
                    title.setBackground(Color.GREEN);
                    break;
                }
                case "travel":{
                    title.setBackground(Color.YELLOW);
                    break;
                }
                case "free_time_activity":{
                    title.setBackground(Color.CYAN);
                    break;
                }
                default:
                    break;
            }
            title.setHorizontalAlignment(SwingConstants.RIGHT);
            title.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 4));
            panel.add(title, BorderLayout.SOUTH);
        }
        for(int i = 0 ; i < noteTitles.size(); i++) {
            JLabel note = new JLabel(noteTitles.get(i));
            note.setHorizontalAlignment(SwingConstants.RIGHT);
            note.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 4));
            panel.add(note, BorderLayout.SOUTH);
        }
        return panel;
    }

    private JPanel createEmptyCell() {
        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
        return panel;
    }
}
