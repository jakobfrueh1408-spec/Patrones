package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * UserPool serves as a centralized collection and manager for all {@link User} accounts.
 * It provides mechanisms to register new users, remove existing accounts, and
 * perform validation to ensure that no duplicate users exist within the system.
 */
public class UserPool {
    /** The internal list of registered users. */
    private ArrayList<User> users;

    /**
     * Constructs an empty UserPool.
     */
    public UserPool() {
        users = new ArrayList<User>();
    }

    /**
     * Adds a new user to the pool if their unique identifier is available.
     * Uses {@link #checkAvailable(User)} to prevent duplicate registrations.
     * * @param user The {@link User} instance to be added.
     */
    public void addUser(User user) {
        if(checkAvailable(user)){
            users.add(user);
        }
    }

    /**
     * Removes a specific user from the pool.
     * * @param user The {@link User} instance to be removed.
     */
    public void removeUser(User user) {
        users.remove(user);
    }

    /**
     * Retrieves the list of all users currently in the pool.
     * * @return An {@link ArrayList} containing all registered users.
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Checks if a user can be added to the pool by verifying if their
     * unique ID number already exists.
     * * @param user The {@link User} to check for duplication.
     * @return true if the ID number is unique (not found in the pool);
     * false otherwise.
     */
    public boolean checkAvailable(User user){
        for (int i = 0; i < users.size(); i++){
            User userToCheck = users.get(i);
            if (userToCheck.getIdNumber().equals(user.getIdNumber())){
                return false;
            }
        }
        return true;
    }
}