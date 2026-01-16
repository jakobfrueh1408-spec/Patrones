package Database;

import Model.Event;
import Model.Note;
import Model.Label;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class EventNoteTableManager implements DatabaseDAO {

    // Hilfsmittel für Datumsformatierung (ISO 8601)
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // --- EVENT OPERATIONEN ---

    public void addEventWithClones(int calendarId, Event baseEvent, int lengthOfOccurrence) {
        // Query angepasst an neues Schema: title, description, date, label
        String sql = "INSERT INTO Events (calendar_id, title, description, date, label) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i <= lengthOfOccurrence; i++) {
                // Hier müsstest du eigentlich die addWeeks Logik anwenden,
                // für den Moment nehmen wir das Datum des Objekts
                String dateStr = sdf.format(baseEvent.getDate());

                pstmt.setInt(1, calendarId);
                pstmt.setString(2, baseEvent.getTitle());
                pstmt.setString(3, baseEvent.getDescription());
                pstmt.setString(4, dateStr);
                pstmt.setString(5, baseEvent.getLabel().toString());

                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void manipulateEvent(Event event) {
        String sql = "UPDATE Events SET title = ?, description = ?, label = ? WHERE event_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, event.getTitle());
            pstmt.setString(2, event.getDescription());
            pstmt.setString(3, event.getLabel().toString());
            pstmt.setInt(4, event.getEvent_Id());
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // --- NOTE OPERATIONEN ---

    public void addNote(int calendarId, String title , String text, Date date) {
        // Query angepasst an Schema: calendar_id, title, text, date
        String sql = "INSERT INTO Notes (calendar_id, title, text, date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, calendarId);
            pstmt.setString(2, title);
            pstmt.setString(3, text);
            pstmt.setString(4, sdf.format(date));

            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void manipulateNote( int note_id,String text) {
        String sql = "UPDATE Notes SET  text = ? WHERE note_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1,text);
            pstmt.setString(2, Integer.toString(note_id));
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // --- SEARCH & FETCH ---

    private ArrayList<Event> fetchEvents(String sql, String param) {
        ArrayList<Event> list = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, param);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // Konvertierung String -> Date
                java.util.Date d = sdf.parse(rs.getString("date"));

                Event e = new Event(
                        rs.getString("title"),
                        rs.getString("description"),
                        d,
                        Label.valueOf(rs.getString("label"))
                );
                e.setEvent_Id(rs.getInt("event_id"));
                list.add(e);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    private ArrayList<Note> fetchNotes(String sql, String param) {
        ArrayList<Note> list = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, param);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                java.util.Date d = sdf.parse(rs.getString("date"));

                Note n = new Note(
                        rs.getString("title"),
                        rs.getString("text"),
                        rs.getDate("date")
                );
                n.setDb_id(rs.getInt("note_id"));

            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // --- DELETE WRAPPERS ---

    public void removeEvent(int eventDbId) {
        executeUpdate("DELETE FROM Events WHERE event_id = ?", eventDbId);
    }

    public void removeNote(int noteDbId) {
        executeUpdate("DELETE FROM Notes WHERE note_id = ?", noteDbId);
    }

    public void removeEvents(ArrayList<Event> eventList) {
        String sql = "DELETE FROM Events WHERE event_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (Event e : eventList) {
                pstmt.setInt(1, e.getEvent_Id());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void executeUpdate(String sql, int id) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}