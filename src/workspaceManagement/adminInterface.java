package workspaceManagement;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class adminInterface {
    private final List<workspace> workspaces = new ArrayList<>();

    public void addWorkspace(workspace workspace) {
        workspaces.add(workspace);
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
        for (workspace ws : workspaces) {
            System.out.printf("%d\t%-15s\t$%.2f%n", ws.getId(), ws.getType(), ws.getPrice());
        }
    }

    public void viewAllReservations() {
        boolean hasReservations = false;

        System.out.println("\nAll Reservations:");
        for (workspace ws : workspaces) {
            List<Reservation> reservations = ws.getReservations();
            if (!reservations.isEmpty()) {
                hasReservations = true;
                System.out.println("\nWorkspace " + ws.getId() + " (" + ws.getType() + "):");
                for (Reservation res : reservations) {
                    System.out.printf("- %s: %s to %s%n",
                            res.getUsername(),
                            res.getStartTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                            res.getEndTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
                }
            }
        }

        if (!hasReservations) {
            System.out.println("No reservations found.");
        }
    }



    public List<workspace> getWorkspaces() {
        return new ArrayList<>(workspaces);
    }
}