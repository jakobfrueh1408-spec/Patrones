package Database;

import Model.Note;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NoteTableManager {
    public void addNote(int calendarId, Note note) {
        String sql = "INSERT INTO Notes (calendar_id, name, note_date, description) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, calendarId);
            pstmt.setString(2, note.getTitle());
            pstmt.setString(3, note.getDate().toInstant().toString());
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
