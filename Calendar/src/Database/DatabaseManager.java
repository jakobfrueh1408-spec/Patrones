package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    // Die URL verweist direkt auf die Datei in deinem Projektordner
    private static final String URL = "jdbc:sqlite:calendar.db";

    /**
     * Stellt eine Verbindung zur SQLite-Datenbank her.
     * @return Connection Objekt
     * @throws SQLException falls die Verbindung fehlschlägt
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    /**
     * Hilfsmethode: Erstellt die Tabellen, falls sie noch nicht existieren.
     * Das ist super für dein "1.u.t." Setup, damit die DB beim Partner sofort bereit ist.
     */
    public static void initializeDatabase() {
        // Wir sammeln alle Statements in einem Array, um sie nacheinander auszuführen
        String[] sqlStatements = {
                // 1. Users Tabelle
                "CREATE TABLE IF NOT EXISTS Users (" +
                        "user_id TEXT PRIMARY KEY, " +
                        "name TEXT NOT NULL, " +
                        "birthday TEXT, " +
                        "password_hash TEXT NOT NULL" +
                        ");",

                // 2. Calendars Tabelle (Beachte: INTEGER PRIMARY KEY für Auto-Inkrement in SQLite)
                "CREATE TABLE IF NOT EXISTS Calendars (" +
                        "calendar_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT NOT NULL, " +
                        "size INTEGER DEFAULT 0, " +
                        "season TEXT, " +
                        "user_id TEXT, " +
                        "FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE" +
                        ");",

                // 3. Events Tabelle
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

                // 4. Notes Tabelle
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

            // SQLite muss Foreign Keys explizit aktivieren!
            stmt.execute("PRAGMA foreign_keys = ON;");

            for (String sql : sqlStatements) {
                stmt.execute(sql);
            }

            System.out.println("Alle Tabellen (Users, Calendars, Events, Notes) wurden erfolgreich initialisiert.");
        } catch (SQLException e) {
            System.err.println("Fehler bei der Initialisierung der Requisa-Datenbank: " + e.getMessage());
        }
    }
}
