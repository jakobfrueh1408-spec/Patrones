package View;

import Model.Calendar;
import Model.Event;
import Model.Note;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

/**
 * DayView is a graphical component that represents a specific day within the calendar.
 * It displays a list of events and notes associated with a given date using a
 * vertical box layout.
 */
public class DayView extends JPanel {

    private final Calendar calendar;
    private final LocalDate date;

    /**
     * Constructs a new DayView panel for a specific date.
     * * @param calendar The calendar model containing the data to be displayed.
     * @param date     The specific date this view represents.
     */
    public DayView(Calendar calendar, LocalDate date) {
        this.calendar = calendar;
        this.date = date;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        buildDay();
    }

    /**
     * Populates the panel with the date header and the list of events and notes.
     * This method clears any existing components before fetching fresh data
     * from the calendar model.
     */
    private void buildDay() {
        removeAll();

        JLabel dateLabel = new JLabel(date.toString());
        dateLabel.setFont(dateLabel.getFont().deriveFont(Font.BOLD, 16f));
        dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(dateLabel);

        add(Box.createVerticalStrut(10));

        // Events
        List<Event> events = calendar.getEventsForDate(date);
        for (Event event : events) {
            add(createEventPanel(event));
        }

        // Notes
        List<Note> notes = calendar.getNotesForDate(date);
        for (Note note : notes) {
            add(createNotePanel(note));
        }

        revalidate();
        repaint();
    }

    /**
     * Creates a stylized JPanel to display individual Event information.
     * The panel includes the event title and a description rendered in HTML.
     * * @param event The event object to be displayed.
     * @return A JPanel containing the event's visual representation.
     */
    private JPanel createEventPanel(Event event) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JLabel title = new JLabel(event.getTitle());
        title.setFont(title.getFont().deriveFont(Font.BOLD));

        JLabel text = new JLabel("<html>" + event.getDescription() + "</html>");

        panel.add(title);
        panel.add(text);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        return panel;
    }

    /**
     * Creates a stylized JPanel to display individual Note information.
     * The panel features a dashed border to distinguish it from events.
     * * @param note The note object to be displayed.
     * @return A JPanel containing the note's visual representation.
     */
    private JPanel createNotePanel(Note note) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createDashedBorder(Color.GRAY));

        JLabel title = new JLabel(note.getTitle());
        title.setFont(title.getFont().deriveFont(Font.BOLD));

        JLabel text = new JLabel("<html>" + note.getText() + "</html>");

        panel.add(title);
        panel.add(text);
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        return panel;
    }
}