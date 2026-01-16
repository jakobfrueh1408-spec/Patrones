package Database;

import Model.Event;
import Model.Note;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EventNoteTableManager implements DatabaseDAO{
    /**
     * Entspricht addEvent(...). Speichert das Basis-Event und alle wöchentlichen Clones.
     */
    public void addEventWithClones(int calendarId, Event baseEvent, int lengthOfOccurrence) {
        String sql = "INSERT INTO Events (calendar_id, name, date_from, date_until, description, event_type) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i <= lengthOfOccurrence; i++) {

                String eventDate = baseEvent.getDate().toString();


                pstmt.setInt(1, calendarId);
                pstmt.setString(2, baseEvent.getTitle());
                pstmt.setString(3, eventDate);
                pstmt.setString(4, eventDate);
                pstmt.setString(5, baseEvent.getDescription());
                pstmt.setString(6, baseEvent.getLabel().toString());

                pstmt.addBatch();
            }
            pstmt.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeEvent(int eventDbId) {
        String sql = "DELETE FROM Events WHERE event_id = ?";
        executeUpdate(sql, eventDbId);
    }

    public void modifyEvent(int eventDbId, String newDescription) {
        String sql = "UPDATE Events SET description = ? WHERE event_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newDescription);
            pstmt.setInt(2, eventDbId);
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void addNote(int calendarId, Note note) {
        String sql = "INSERT INTO Notes (calendar_id, name, note_date, description) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, calendarId);
            pstmt.setString(2, note.getTitle());
            pstmt.setString(3, note.getDate().toString());
            pstmt.setString(4, note.getText());

            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void modifyNote(int noteDbId, String newText) {
        String sql = "UPDATE Notes SET description = ? WHERE note_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newText);
            pstmt.setInt(2, noteDbId);
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // Hilfsmethode für einfache Deletes
    private void executeUpdate(String sql, int id) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
