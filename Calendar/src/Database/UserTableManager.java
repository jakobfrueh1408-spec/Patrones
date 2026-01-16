package Database;

import Model.Calendar;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserTableManager implements DatabaseDAO{


    public void register(User user){

        String sql = "INSERT INTO USERS (user_id, name,birthday, password_hash) VALUES(?,?,?,?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

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

                User user = new User(
                        rs.getString("name"),
                        rs.getString("password_hash"),
                        rs.getString("birthday")
                );

                return user;
            }
        } catch (SQLException e) {
            System.err.println("Login-Fehler: " + e.getMessage());
        }
        return null;
    }
    public static void main(String[]args){
        User user = new User("jakot","1234", "14.08.2004");
        UserTableManager manager  = new UserTableManager();
        manager.register(user);
    }
}
