package Database;

import Model.Calendar;
import Model.Season;

import java.sql.*;
import java.util.ArrayList;

public class CalendarTableManager implements DatabaseDAO{

    public int addCalendar(String name, String season, int year, String userId) {
        String sql = "INSERT INTO Calendars (name, season, user_id) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, name);
            pstmt.setString(2, season);
            pstmt.setString(3, userId);
            pstmt.executeUpdate();

            // Wir brauchen die neue ID aus der DB für spätere Operationen
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return -1;
    }

    // 2. removeCalendar: Löscht Kalender (Cascade löscht Events/Notes automatisch)
    public void removeCalendar(int calendarId) {
        String sql = "DELETE FROM Calendars WHERE calendar_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, calendarId);
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void modifyCalendar(int calendarId, String newTitle) {
        String sql = "UPDATE Calendars SET name = ? WHERE calendar_id = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newTitle);
            pstmt.setInt(2, calendarId);
            pstmt.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public ArrayList<Calendar> loadAllCalendarsForUser(String userId, int year) {
        ArrayList<Calendar> calendars = new ArrayList<>();
        String sql = "SELECT * FROM Calendars WHERE user_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String seasonStr = rs.getString("season");
                int id = rs.getInt("calendar_id");

                Calendar cal = new Calendar(1, name, Season.valueOf(seasonStr), year);

                calendars.add(cal);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return calendars;
    }
}
