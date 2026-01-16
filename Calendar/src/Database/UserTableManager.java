package Database;

import Model.Calendar;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserTableManager {


    public void register(User user){

        String sql = "INSERT INTO USERS (user_id, name,birthday, password_hash) VALUES(?,?,?,?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Mapping von Model-Attributen auf SQL-Parameter
            pstmt.setString(1, user.getIdNumber());
            pstmt.setString(2, user.getUserName());
            pstmt.setString(3, user.getBirthDate());
            pstmt.setString(4, user.getPassword());

            pstmt.executeUpdate();
            System.out.println("User " + user.getUserName() + " was stored in the db.");

        } catch (SQLException e) {
            System.err.println("Fehler beim Speichern des Users: " + e.getMessage());
        }
    }

    public void deleteUser(String userId) {
        String sql = "DELETE FROM Users WHERE user_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // SQLite: Sicherstellen, dass Foreign Keys aktiv sind für Cascade Delete
            pstmt.executeUpdate("PRAGMA foreign_keys = ON;");

            pstmt.setString(1, userId);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("User was deleted.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting User: " + e.getMessage());
        }
    }
    public User getUserByLogin(String name, String passwordHash) {
        String sql = "SELECT * FROM Users WHERE name = ? AND password_hash = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, passwordHash);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Wir bauen das User-Objekt aus den DB-Daten wieder zusammen
                User user = new User(
                        rs.getString("name"),
                        rs.getString("password_hash"),
                        rs.getString("birthday")
                );
                // Die ID wird automatisch über den Hash im Konstruktor gesetzt
                return user;
            }
        } catch (SQLException e) {
            System.err.println("Login-Fehler: " + e.getMessage());
        }
        return null; // Kein User gefunden oder Fehler
    }
    public static void main(String[]args){
        User user = new User("jakot","1234", "14.08.2004");
        UserTableManager manager  = new UserTableManager();
        manager.register(user);
    }
}
