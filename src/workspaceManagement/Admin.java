package workspaceManagement;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Admin {
    private final List<Workspace> workspaces = new ArrayList<>();

    public void addWorkspace(Workspace workspace) {
        workspaces.add(workspace);
    }
    public void setWorkspaces(List<Workspace> wk){
        workspaces.clear();
        workspaces.addAll(wk);
    }
    public boolean removeWorkspace(int id) {
        return workspaces.removeIf(ws -> ws.getId() == id);
    }

    public void viewAllWorkspaces() {
        if (workspaces.isEmpty()) {
            System.out.println("No workspaces available.");
            return;
        }

        System.out.println("\nAvailable Workspaces:");
        System.out.println("ID\tType\t\tPrice/Hour");
        for (Workspace ws : workspaces) {
            System.out.printf("%d\t%-15s\t$%.2f%n", ws.getId(), ws.getType(), ws.getPrice());
        }
    }

    public void viewAllReservations() {
            List<Workspace> reservedWorkspaces = workspaces.stream()
                    .filter(ws -> !ws.getReservations().isEmpty())
                    .toList();

            if (reservedWorkspaces.isEmpty()) {
                System.out.println("No reservations found.");
                return;
            }

            reservedWorkspaces.forEach(ws -> {
                System.out.println("\nWorkspace " + ws.getId() + " (" + ws.getType() + "):");
                ws.getReservations().forEach(res ->
                        System.out.printf("- %s: %s to %s%n",
                                res.getUsername(),
                                res.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                                res.getEndTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
            });
        }




    public List<Workspace> getWorkspaces() {
        return new ArrayList<>(workspaces);
    }
}