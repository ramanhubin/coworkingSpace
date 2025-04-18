package workspaceManagement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class UsersBase  implements Serializable
{
    private final HashMap<String, User> users = new HashMap<>();
    public User getOrCreateUser(String userName) {
        return users.computeIfAbsent(userName, User::new);
    }
    public boolean userExist(String username) {
       return users.containsKey(username);

    }

    public Optional<User> getUser(String username) {
        return Optional.ofNullable(users.get(username));
    }

}
