package Database;

import Model.Event;
import Model.Note;
import Model.Label;
import java.sql.*;
import java.time.LocalDate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * EventNoteTableManager handles the Data Access Object (DAO) operations for both
 * the 'Events' and 'Notes' tables.
 * <p>
 * It provides methods to create, modify, fetch, and remove daily calendar entries.
 * This manager uses {@link LocalDate} for date handling, ensuring compatibility
 * with modern Java time APIs and ISO 8601 string storage in SQLite.
 * </p>
 */
public class EventNoteTableManager implements DatabaseDAO {

    /** Formatter for ISO 8601 date-time strings. */
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // --- EVENT OPERATIONS ---

    /**
     * Persists a new event to the database.
     * * @param calendarId         The ID of the calendar this event belongs to.
     * @param title              The title of the event.
     * @param description        A detailed description of the event.
     * @param date               The specific date for the event.
     * @param label              The category label (e.g., WORK, PERSONAL).
     * @param lengthOfOccurrence Reserved for recurring events logic.
     */
    public void addEventsToDB(int calendarId, String title, String description, LocalDate date, Label label, int lengthOfOccurrence) {
        String sql = "INSERT INTO Events (calendar_id, title, description, date, label) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String dateStr = date.toString(); // Result: "YYYY-MM-DD"

            pstmt.setInt(1, calendarId);
            pstmt.setString(2, title);
            pstmt.setString(3, description);
            pstmt.setString(4, dateStr);
            pstmt.setString(5, label.toString());

            pstmt.addBatch();
            pstmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the description of an existing event.
     * * @param event_id    The database ID of the event to modify.
     * @param description The new text description.
     */
    public void manipulateEvent(int event_id, String description) {
        String sql = "UPDATE Events SET description = ? WHERE event_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, description);
            pstmt.setInt(2, event_id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- NOTE OPERATIONS ---

    /**
     * Adds a new note to a specific calendar date.
     * * @param calendarId The ID of the parent calendar.
     * @param title      The title of the note.
     * @param text       The body content of the note.
     * @param date       The date associated with the note.
     */
    public void addNote(int calendarId, String title, String text, LocalDate date) {
        String sql = "INSERT INTO Notes (calendar_id, title, text, date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String dateStr = date.toString();

            pstmt.setInt(1, calendarId);
            pstmt.setString(2, title);
            pstmt.setString(3, text);
            pstmt.setString(4, dateStr);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the text content of an existing note.
     * * @param note_id The database ID of the note.
     * @param text    The new body text.
     */
    public void manipulateNote(int note_id, String text) {
        String sql = "UPDATE Notes SET text = ? WHERE note_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, text);
            pstmt.setInt(2, note_id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- SEARCH & FETCH ---

    /**
     * Retrieves all events associated with a specific calendar.
     * * @param calendarId The ID of the calendar to query.
     * @return A list of {@link Event} objects.
     */
    public ArrayList<Event> fetchEvents(int calendarId) {
        String sql = "SELECT event_id, title, description, date, label FROM Events WHERE calendar_id = ?";
        ArrayList<Event> list = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, calendarId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                LocalDate d = LocalDate.parse(rs.getString("date"));

                Event e = new Event(
                        rs.getString("title"),
                        rs.getString("description"),
                        d,
                        Label.valueOf(rs.getString("label"))
                );
                e.setEvent_Id(rs.getInt("event_id"));
                list.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Retrieves all notes associated with a specific calendar.
     * * @param calendarId The ID of the calendar to query.
     * @return A list of {@link Note} objects.
     */
    public ArrayList<Note> fetchNotes(int calendarId) {
        String sql = "SELECT note_id, title, text, date FROM Notes WHERE calendar_id = ?";
        ArrayList<Note> list = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, calendarId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                LocalDate d = LocalDate.parse(rs.getString("date"));

                Note n = new Note(
                        rs.getString("title"),
                        rs.getString("text"),
                        d
                );
                n.setDb_id(rs.getInt("note_id"));
                list.add(n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /** Removes a single event by its database ID. */
    public void removeEvent(int eventDbId) {
        executeUpdate("DELETE FROM Events WHERE event_id = ?", eventDbId);
    }

    /** Removes a single note by its database ID. */
    public void removeNote(int noteDbId) {
        executeUpdate("DELETE FROM Notes WHERE note_id = ?", noteDbId);
    }

    /** * Batch deletes a list of events from the database.
     * @param eventList The list of events to be deleted.
     */
    public void removeEvents(ArrayList<Event> eventList) {
        String sql = "DELETE FROM Events WHERE event_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (Event e : eventList) {
                pstmt.setInt(1, e.getEvent_Id());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Private helper to execute ID-based deletions. */
    private void executeUpdate(String sql, int id) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}