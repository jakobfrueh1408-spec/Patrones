package Database;

import Model.Calendar;
import Model.Season;

import java.sql.*;
import java.util.ArrayList;

/**
 * CalendarTableManager is a Data Access Object (DAO) that handles all SQL operations
 * for the 'Calendars' table.
 * <p>
 * It provides methods to create, retrieve, update, and delete calendar records,
 * ensuring that the application's {@link Model.Calendar} objects are correctly
 * persisted in the relational database.
 * </p>
 */
public class CalendarTableManager implements DatabaseDAO {

    /**
     * Adds a new calendar record to the database.
     * * @param name     The display name of the calendar.
     * @param length   The number of days in the calendar.
     * @param season   The starting season (stored as a String).
     * @param year     The designated year for the calendar.
     * @param userId   The ID of the user who owns this calendar.
     * @return The auto-generated integer ID from the database, or -1 if the insertion fails.
     */
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

            // Return the generated INTEGER ID
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Removes a calendar from the database based on its ID.
     * Note: Due to FOREIGN KEY constraints with CASCADE, removing a calendar
     * will also delete all associated events and notes.
     * * @param calendarId The unique database ID of the calendar to be deleted.
     */
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

    /**
     * Updates the name of an existing calendar.
     * * @param calendarId The unique database ID of the calendar.
     * @param newTitle   The new name to be assigned.
     */
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

    /**
     * Retrieves all calendars associated with a specific user.
     * Maps database rows to {@link Calendar} objects.
     * * @param userId The ID of the user whose calendars should be loaded.
     * @return An ArrayList of Calendar objects populated with database data.
     */
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

                // Create the object with loaded data
                Calendar cal = new Calendar(length, name, Season.valueOf(seasonStr), year);

                // Important: Set the DB ID so we can identify this specific calendar
                // for future operations like deletion or modification.
                cal.setDb_id(dbId);

                calendars.add(cal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return calendars;
    }

    /**
     * Helper function to find a specific calendar by its name for a given user.
     * * @param name   The name of the calendar to search for.
     * @param userId The ID of the owner user.
     * @return A populated Calendar object if found, otherwise null.
     */
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