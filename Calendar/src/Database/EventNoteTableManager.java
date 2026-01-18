package Database;

import Model.Event;
import Model.Note;
import Model.Label;
import java.sql.*;
import java.time.LocalDate;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class EventNoteTableManager implements DatabaseDAO {

    // Hilfsmittel für Datumsformatierung (ISO 8601)
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // --- EVENT OPERATIONEN ---

    public void addEventsToDB(int calendarId, String title  , String description, LocalDate date, Label label, int lengthOfOccurrence) {
        // Query angepasst an neues Schema: title, description, date, label
        String sql = "INSERT INTO Events (calendar_id, title, description, date, label) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

                // Hier müsstest du eigentlich die addWeeks Logik anwenden,
                // für den Moment nehmen wir das Datum des Objekts


                String dateStr = date.toString(); // Ergebnis: "2026-01-17"

                pstmt.setInt(1, calendarId);
                pstmt.setString(2, title);
                pstmt.setString(3, description);
                pstmt.setString(4, dateStr);
                pstmt.setString(5, label.toString());

                pstmt.addBatch();

            pstmt.executeBatch();
        } catch (SQLException e) { e.printStackTrace(); }

    }

    public void manipulateEvent(int event_id, String description) {
        String sql = "UPDATE Events SET  description = ? WHERE event_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, description);
            pstmt.setInt(2, event_id);
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // --- NOTE OPERATIONEN ---

    public void addNote(int calendarId, String title , String text, LocalDate date) {
        // Query angepasst an Schema: calendar_id, title, text, date
        String sql = "INSERT INTO Notes (calendar_id, title, text, date) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String dateStr = date.toString();

            pstmt.setInt(1, calendarId);
            pstmt.setString(2, title);
            pstmt.setString(3, text);
            pstmt.setString(4, dateStr);

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
                // Konvertierung String -> LocalDate
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
                LocalDate d = LocalDate.parse(rs.getString("date"));

                Note n = new Note(
                        rs.getString("title"),
                        rs.getString("text"),
                        d // <-- use LocalDate, not java.sql.Date
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
