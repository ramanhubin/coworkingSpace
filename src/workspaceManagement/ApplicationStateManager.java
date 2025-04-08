package workspaceManagement;

import java.io.*;
import java.util.List;
public class ApplicationStateManager implements Serializable {

    private usersBase usersBase;
    private List<workspace> workspaces;

    public ApplicationStateManager(usersBase usersBase, List<workspace> workspaces) {
        this.usersBase = usersBase;
        this.workspaces = workspaces;
    }

    public void saveData(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        }
    }

    public static ApplicationStateManager loadData(String filename)
            throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (ApplicationStateManager) ois.readObject();
        }
    }


    public usersBase getUsersBase() {
        return usersBase;
    }

    public List<workspace> getWorkspaces() {
        return workspaces;
    }
}