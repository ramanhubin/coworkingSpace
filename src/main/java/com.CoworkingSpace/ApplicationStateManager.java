package com.CoworkingSpace;

import java.io.*;
import java.util.List;

public class ApplicationStateManager implements Serializable {

    private UsersBase usersBase;
    private List<Workspace> workspaces;


    public ApplicationStateManager(UsersBase usersBase, List<Workspace> workspaces) {
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

    public UsersBase getUsersBase() {
        return usersBase;
    }

    public List<Workspace> getWorkspaces() {
        return workspaces;
    }
}