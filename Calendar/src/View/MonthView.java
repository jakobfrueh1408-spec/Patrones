package View;

import Model.Calendar;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;

/**
 * MonthView is a graphical component that provides a grid-based representation of a full month.
 * It displays days organized by weeks, including headers for the days of the week,
 * and populates individual day cells with event titles (color-coded by category) and note titles.
 */
public class MonthView extends JPanel {

    private final LocalDate displayedMonth; // always the 1st of the month
    private final Calendar calendar;

    /**
     * Constructs a new MonthView panel.
     * * @param displayedMonth A LocalDate within the month to be displayed (normalized to the 1st day).
     * @param calendar       The calendar model used to retrieve events, labels, and notes.
     */
    public MonthView(LocalDate displayedMonth, Calendar calendar) {
        this.displayedMonth = displayedMonth.withDayOfMonth(1);
        this.calendar = calendar;

        setLayout(new GridLayout(0, 7));
        buildMonth();
    }

    /**
     * Constructs the visual grid for the month.
     * This method handles the creation of the day-of-week headers, calculates the
     * necessary padding for the first week, and iterates through all days of the
     * month to add their respective cells.
     */
    private void buildMonth() {
        removeAll();

        // Header: Monday–Sunday
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

        // Empty cells at the start of the month
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

    /**
     * Creates a JPanel representing a single day in the month grid.
     * Events are rendered with background colors based on their labels (e.g., sport, study),
     * and notes are listed below the events.
     * * @param date         The date of the cell.
     * @param eventTitles  List of titles for events occurring on this day.
     * @param labelTitles  List of labels corresponding to each event for color-coding.
     * @param noteTitles   List of titles for notes associated with this day.
     * @return A JPanel configured with day number and content summaries.
     */
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

            // Color-coding based on label type
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

    /**
     * Creates an empty placeholder cell used to align the first day of the month
     * with the correct day-of-week column.
     * * @return A blank JPanel with a light gray border.
     */
    private JPanel createEmptyCell() {
        JPanel panel = new JPanel();
        panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
        return panel;
    }
}