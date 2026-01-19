package Database;

import Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * UserTableManager handles the Data Access Object (DAO) operations for the 'Users' table.
 * <p>
 * This class provides the primary interface for user authentication and account management,
 * including registration, deletion, and credential verification.
 * </p>
 */
public class UserTableManager implements DatabaseDAO {

    /**
     * Registers a new user by persisting their profile data to the database.
     * * @param user The {@link User} object containing the registration details.
     */
    public void register(User user) {
        String sql = "INSERT INTO USERS (user_id, name, birthday, password_hash) VALUES(?,?,?,?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getIdNumber());
            pstmt.setString(2, user.getUserName());
            pstmt.setString(3, user.getBirthDate());
            pstmt.setString(4, user.getPassword());

            pstmt.executeUpdate();
            System.out.println("User " + user.getUserName() + " was stored in the database.");

        } catch (SQLException e) {
            System.err.println("Error saving user: " + e.getMessage());
        }
    }

    /**
     * Deletes a user account based on their unique ID.
     * <p>
     * Note: This operation triggers a cascade delete in the database,
     * removing all associated calendars, events, and notes.
     * </p>
     * * @param userId The unique identifier of the user to be removed.
     */
    public void deleteUser(String userId) {
        String sql = "DELETE FROM Users WHERE user_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Ensure foreign key constraints are active for cascading deletes
            pstmt.executeUpdate("PRAGMA foreign_keys = ON;");

            pstmt.setString(1, userId);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("User was successfully deleted.");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
        }
    }

    /**
     * Retrieves a user from the database if the provided credentials are valid.
     * * @param name         The username provided during login.
     * @param passwordHash The hashed password provided during login.
     * @return A populated {@link User} object if credentials match; otherwise null.
     */
    public User getUserByLogin(String name, String passwordHash) {
        String sql = "SELECT * FROM Users WHERE name = ? AND password_hash = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, passwordHash);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Constructing user from database result
                User user = new User(
                        rs.getString("name"),
                        rs.getString("password_hash"),
                        rs.getString("birthday")
                );
                return user;
            }
        } catch (SQLException e) {
            System.err.println("Login Error: " + e.getMessage());
        }
        return null;
    }

    /**
     * Main method for testing user registration logic independently.
     */
    public static void main(String[] args) {
        User user = new User("jakot", "1234", "14.08.2004");
        UserTableManager manager = new UserTableManager();
        manager.register(user);
    }
}