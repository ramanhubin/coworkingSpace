package workspaceManagement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UsersBase implements Serializable
{
    //private final List<User> users =new ArrayList<User>();
    private static int userId = 0;
    private final HashMap<String, User> users = new HashMap<>();
    public void addUser(User user){
        users.put(user.getUserName(), user);
    }
    public boolean userExist(String username) {
       return users.containsKey(username);

    }

    public User getUser(String username) {
        return users.get(username);
    }

}
