package workspaceManagement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UsersBase implements Serializable
{
    private final List<User> users =new ArrayList<User>();

    public void addUser(User user){
        users.add(user);
    }
    public boolean userExist(String username) {
        for(User us: users) {
            if(us.getUserName().equals(username)) { // Используем equals() вместо ==
                return true;
            }
        }
        return false;
    }

    public User getUser(String username) {
        for (User user : users) {
            if (user.getUserName().equals(username)) {
                return user;
            }
        }
        return null;
    }


}
