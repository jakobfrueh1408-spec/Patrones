package Database;

import Model.User;
import java.sql.*;

/**
 * DatabaseManager serves as the central utility class for managing the SQLite database connection.
 * <p>
 * It handles the connection string, provides a mechanism for obtaining active {@link Connection}
 * objects, and ensures the database schema (Users, Calendars, Events, and Notes tables)
 * is correctly initialized upon application startup.
 * </p>
 */
public class DatabaseManager implements DatabaseDAO {
    /** The JDBC URL pointing to the local SQLite database file. */
    private static final String URL = "jdbc:sqlite:calendar.db";

    /**
     * Establishes a connection to the SQLite database.
     * * @return A {@link Connection} object to the database.
     * @throws SQLException if the connection attempt fails.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    /**
     * Helper method to create the required database tables if they do not exist.
     * <p>
     * This ensures the application is "ready to run" immediately on a new system
     * by setting up the relational structure and enabling foreign key constraints.
     * </p>
     */
    public static void initializeDatabase() {

        String[] sqlStatements = {
                // 1. Users Table
                "CREATE TABLE IF NOT EXISTS Users (" +
                        "user_id TEXT PRIMARY KEY, " +
                        "name TEXT NOT NULL, " +
                        "birthday TEXT, " +
                        "password_hash TEXT NOT NULL" +
                        ");",

                // 2. Calendars Table
                "CREATE TABLE IF NOT EXISTS Calendars (" +
                        "calendar_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT NOT NULL, " +
                        "size INTEGER DEFAULT 0, " +
                        "season TEXT, " +
                        "user_id TEXT, " +
                        "FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE" +
                        ");",

                // 3. Events Table
                "CREATE TABLE IF NOT EXISTS Events (" +
                        "event_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "calendar_id INTEGER, " +
                        "name TEXT NOT NULL, " +
                        "date_from TEXT NOT NULL, " +
                        "date_until TEXT NOT NULL, " +
                        "description TEXT, " +
                        "event_type TEXT, " +
                        "FOREIGN KEY (calendar_id) REFERENCES Calendars(calendar_id) ON DELETE CASCADE" +
                        ");",

                // 4. Notes Table
                "CREATE TABLE IF NOT EXISTS Notes (" +
                        "note_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "calendar_id INTEGER, " +
                        "name TEXT NOT NULL, " +
                        "note_date TEXT NOT NULL, " +
                        "description TEXT, " +
                        "note_type TEXT, " +
                        "FOREIGN KEY (calendar_id) REFERENCES Calendars(calendar_id) ON DELETE CASCADE" +
                        ");"
        };

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Enable Foreign Key support in SQLite
            stmt.execute("PRAGMA foreign_keys = ON;");

            for (String sql : sqlStatements) {
                stmt.execute(sql);
            }

            System.out.println("All Tables have been successfully initialized.");
        } catch (SQLException e) {
            System.err.println("Database Initialization Error: " + e.getMessage());
        }
    }
}