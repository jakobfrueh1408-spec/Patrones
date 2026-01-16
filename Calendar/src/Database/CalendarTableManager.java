package Database;

import Model.Calendar;
import Model.Season;

import java.sql.*;
import java.util.ArrayList;

public class CalendarTableManager implements DatabaseDAO {

    // 1. addCalendar: Berücksichtigt jetzt length und year
    public int addCalendar(String name, int length, String season, int year, String userId) {
        String sql = "INSERT INTO Calendars (user_id, name, length, year, season) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, userId);
            pstmt.setString(2, name);
            pstmt.setInt(3, length);
            pstmt.setInt(4, year);
            pstmt.setString(5, season);
            pstmt.executeUpdate();

            // Rückgabe der generierten INTEGER ID
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // 2. removeCalendar (Bleibt gleich, löscht dank CASCADE alles andere mit)
    public void removeCalendar(int calendarId) {
        String sql = "DELETE FROM Calendars WHERE calendar_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, calendarId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. modifyCalendar
    public void modifyCalendar(int calendarId, String newTitle) {
        String sql = "UPDATE Calendars SET name = ? WHERE calendar_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newTitle);
            pstmt.setInt(2, calendarId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4. loadAllCalendarsForUser: Mappt alle neuen Spalten in das Objekt
    public ArrayList<Calendar> loadAllCalendarsForUser(String userId) {
        ArrayList<Calendar> calendars = new ArrayList<>();
        String sql = "SELECT * FROM Calendars WHERE user_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int dbId = rs.getInt("calendar_id");
                String name = rs.getString("name");
                int length = rs.getInt("length");
                int year = rs.getInt("year");
                String seasonStr = rs.getString("season");

                // Erstellen des Objekts mit den geladenen Daten
                Calendar cal = new Calendar(length, name, Season.valueOf(seasonStr), year);

                // Wichtig: Setzen der DB-ID, damit wir diesen Kalender später wiederfinden (z.B. zum Löschen)
                cal.setDb_id(dbId);

                calendars.add(cal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return calendars;
    }

    // 5. Hilfsfunktion: Kalender nach Name suchen (wie von dir angefordert)
    public Calendar getCalendarByName(String name, String userId) {
        String sql = "SELECT * FROM Calendars WHERE name = ? AND user_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Calendar cal = new Calendar(
                        rs.getInt("length"),
                        rs.getString("name"),
                        Season.valueOf(rs.getString("season")),
                        rs.getInt("year")
                );
                cal.setDb_id(rs.getInt("calendar_id"));
                return cal;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
