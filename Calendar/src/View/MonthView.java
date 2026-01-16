package View;

import Model.Calendar;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

public class MonthView extends JPanel {

    private final YearMonth yearMonth;
    private Calendar calendar;

    public MonthView(int month, Calendar calendar) {
        int year = calendar.getYear();
        this.calendar = calendar;
        if((calendar.getSeason().toString() == "Autumn" && month >= 5) || (calendar.getSeason().toString() == "Spring" && month >= 11)){
            year++;
        }
        this.yearMonth = YearMonth.of(year, month);

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

        LocalDate firstDay = yearMonth.atDay(1);
        int firstDayColumn = firstDay.getDayOfWeek().getValue() % 7;

        for (int i = 0; i < firstDayColumn; i++) {
            add(createEmptyCell());
        }

        int daysInMonth = yearMonth.lengthOfMonth();
        for (int day = 1; day <= daysInMonth; day++) {

            add(createDayCell(day));
        }

        revalidate();
        repaint();
    }


    private JPanel createDayCell(int day, String[] titles) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new LineBorder(Color.LIGHT_GRAY));

        JLabel label = new JLabel(String.valueOf(day));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 4));

        panel.add(label, BorderLayout.NORTH);

        for(int i = 0 ; i < titles.length; i++) {
            JLabel title = new JLabel(titles[i]);
            title.setHorizontalAlignment(SwingConstants.RIGHT);
            title.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 4));
            panel.add(title, BorderLayout.SOUTH);
        }
        return panel;
    }

    private JPanel createEmptyCell() {
        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
        return panel;
    }
    private String[] dayTitles(int day){
        for(int i = 0 ; i < calendar.getNotes().size(); i++){
            if(calendar.getNotes().get(i).getDate().getCurrentDay == i){

            }
        }
    }
}
