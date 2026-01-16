package View;

import Model.Calendar;
import Model.Event;
import Model.Note;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class DayView extends JPanel {

    private final Calendar calendar;
    private final LocalDate date;

    public DayView(Calendar calendar, LocalDate date) {
        this.calendar = calendar;
        this.date = date;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        buildDay();
    }

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

