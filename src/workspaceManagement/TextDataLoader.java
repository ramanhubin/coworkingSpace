package workspaceManagement;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TextDataLoader {
    private static final String DATA_FILE = "workspaces.txt";
    private static final String SEPARATOR = "|";
    private static List<Workspace> loadedWorkspaces;


    public static List<Workspace> loadOnStartup() {
        if (loadedWorkspaces != null) {
            return loadedWorkspaces;
        }

        loadedWorkspaces = new ArrayList<>();
        File file = new File(DATA_FILE);

        if (!file.exists()) {
            System.out.println("[System] Data file not found. Starting with empty workspace list.");
            return loadedWorkspaces;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Workspace ws = parseWorkspace(line);
                if (ws != null) {
                    loadedWorkspaces.add(ws);
                }
            }
            System.out.printf("[System] Loaded %d workspaces from '%s'%n",
                    loadedWorkspaces.size(), DATA_FILE);
        } catch (IOException e) {
            System.out.println("[Error] Failed to load workspaces: " + e.getMessage());
        }
        return loadedWorkspaces;
    }


    public static void saveOnExit(List<Workspace> workspaces) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_FILE))) {
            for (Workspace ws : workspaces) {
                writer.write(String.format("%d%s%s%s%.2f",
                        ws.getId(), SEPARATOR,
                        ws.getType(), SEPARATOR,
                        ws.getPrice()));
                writer.newLine();
            }
            System.out.printf("[System] Saved %d workspaces to '%s'%n",
                    workspaces.size(), DATA_FILE);
        } catch (IOException e) {
            System.out.println("[Error] Failed to save workspaces: " + e.getMessage());
        }
    }

    private static Workspace parseWorkspace(String line) {
        try {
            String[] parts = line.split("\\" + SEPARATOR, 3);
            if (parts.length != 3) {
                throw new IllegalArgumentException("Invalid data format");
            }
            return new Workspace(
                    Integer.parseInt(parts[0].trim()),
                    parts[1].trim(),
                    Float.parseFloat(parts[2].trim())
            );
        } catch (Exception e) {
            System.out.println("[Warning] Skipping invalid entry: " + line);
            return null;
        }
    }
}