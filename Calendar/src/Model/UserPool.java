package Model;

import java.util.ArrayList;
import java.util.List;

public class UserPool {
    private ArrayList<User> users;
    public UserPool() {
        users = new ArrayList<User>();
    }
    public void addUser(User user) {
        if(checkAvailable(user)){
            users.add(user);
        }
     
    }
    public void removeUser(User user) {
        users.remove(user);
    }
    public ArrayList<User> getUsers() {
        return users;
    }

    //check if we have a duplicate
    public boolean checkAvailable(User user){
 
    for ( int i = 0; i < users.size(); i++){
        User userToCheck = users.get(i); 
            if ( userToCheck.getIdNumber() == user.getIdNumber()){
                return false; 
            }
        }

    return true;
    }
}
