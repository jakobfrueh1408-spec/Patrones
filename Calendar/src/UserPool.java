import java.util.ArrayList;
import java.util.List;

public class UserPool {
    private List<User> users;
    public UserPool() {
        users = new ArrayList<User>();
    }
    public void addUser(User user) {
        users.add(user);
    }
    public void removeUser(User user) {
        users.remove(user);
    }
    public List<User> getUsers() {
        return users;
    }
}
