package workspaceManagement;

import java.util.ArrayList;
import java.util.List;

public class usersBase
{
    private final List<userInterface> users =new ArrayList<userInterface>();



    public void addUser(userInterface user){
        users.add(user);
    }
    public boolean userExist(String username) {
        for(userInterface us: users) {
            if(us.getUserName().equals(username)) { // Используем equals() вместо ==
                return true;
            }
        }
        return false;
    }

    public userInterface getUser(String username) {
        for (userInterface user : users) {
            if (user.getUserName().equals(username)) {
                return user;
            }
        }
        return null;
    }


}
