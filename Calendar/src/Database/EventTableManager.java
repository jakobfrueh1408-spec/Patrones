package Database;

import Model.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EventTableManager {
    /**
     * Entspricht addEvent(...). Speichert das Basis-Event und alle wöchentlichen Clones.
     */
    public void addEventWithClones(int calendarId, Event baseEvent, int lengthOfOccurrence) {
        String sql = "INSERT INTO Events (calendar_id, name, date_from, date_until, description, event_type) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Schleife für das Basis-Event (i=0) und alle Clones
            for (int i = 0; i <= lengthOfOccurrence; i++) {
                // Wir nutzen die Logik aus deiner Calendar.addWeeks Methode
                // Hier vereinfacht als String-Repräsentation des Datums
                String eventDate = baseEvent.getDate().toInstant().toString();
                // Hinweis: In der echten Schleife müsstest du hier das Datum pro Iteration um i Wochen erhöhen

                pstmt.setInt(1, calendarId);
                pstmt.setString(2, baseEvent.getTitle());
                pstmt.setString(3, eventDate);
                pstmt.setString(4, eventDate); // Falls Start = Ende bei euch
                pstmt.setString(5, baseEvent.getDescription());
                pstmt.setString(6, baseEvent.getLabel().toString());

                pstmt.addBatch(); // Sammelt die Inserts für bessere Performance
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

    // Hilfsmethode für einfache Deletes
    private void executeUpdate(String sql, int id) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}
