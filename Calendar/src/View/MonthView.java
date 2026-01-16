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

    private final LocalDate displayedMonth; // mindig a hónap 1-je
    private final Calendar calendar;

    public MonthView(LocalDate displayedMonth, Calendar calendar) {
        this.displayedMonth = displayedMonth.withDayOfMonth(1);
        this.calendar = calendar;

        setLayout(new GridLayout(0, 7));
        buildMonth();
    }

    private void buildMonth() {
        removeAll();

        // Fejléc: Hétfő–Vasárnap
        for (DayOfWeek day : DayOfWeek.values()) {
            JLabel header = new JLabel(
                    day.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                    SwingConstants.CENTER
            );
            header.setFont(header.getFont().deriveFont(Font.BOLD));
            add(header);
        }

        LocalDate firstDayOfMonth = displayedMonth;
        int firstDayColumn = firstDayOfMonth.getDayOfWeek().getValue() % 7;

        // Üres cellák a hónap elején
        for (int i = 0; i < firstDayColumn; i++) {
            add(createEmptyCell());
        }

        int daysInMonth = firstDayOfMonth.lengthOfMonth();

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = firstDayOfMonth.withDayOfMonth(day);

            add(createDayCell(
                    date,
                    calendar.dayEventTitles(date),
                    calendar.dayLabelTitles(date),
                    calendar.dayNoteTitles(date)
            ));
        }

        revalidate();
        repaint();
    }


    private JPanel createDayCell(
            LocalDate date,
            ArrayList<String> eventTitles,
            ArrayList<String> labelTitles,
            ArrayList<String> noteTitles) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new LineBorder(Color.LIGHT_GRAY));

        JLabel dayLabel = new JLabel(String.valueOf(date.getDayOfMonth()));
        dayLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        dayLabel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 4));
        panel.add(dayLabel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        for (int i = 0; i < eventTitles.size(); i++) {
            JLabel title = new JLabel(eventTitles.get(i));
            title.setOpaque(true);

            switch (labelTitles.get(i)) {
                case "sport" -> title.setBackground(Color.RED);
                case "study" -> title.setBackground(Color.GREEN);
                case "travel" -> title.setBackground(Color.YELLOW);
                case "free_time_activity" -> title.setBackground(Color.CYAN);
            }

            contentPanel.add(title);
        }

        for (String noteTitle : noteTitles) {
            JLabel note = new JLabel(noteTitle);
            contentPanel.add(note);
        }

        panel.add(contentPanel, BorderLayout.CENTER);
        return panel;
    }



    private JPanel createEmptyCell() {
        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
        return panel;
    }
}
