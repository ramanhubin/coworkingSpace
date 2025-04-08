package workspaceManagement;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class userInterface implements Serializable {
    private final String userName;
    private final List<Reservation> userReservations;

    public userInterface(String userName) {
        this.userName = userName;
        this.userReservations = new ArrayList<>();

    }


    public boolean makeReservation(LocalDateTime startTime, LocalDateTime endTime,
                                   int workspaceId, List<workspace> workspaces) {
        if (startTime.isAfter(endTime)) {
            System.out.println("Error: Start time must be before end time.");
            return false;
        }

        for (workspace ws : workspaces) {
            if (ws.getId() == workspaceId && ws.checkAvailability(startTime, endTime)) {
                Reservation reservation = new Reservation(userName, startTime, endTime, workspaceId);
                ws.addReservation(reservation);
                userReservations.add(reservation);
                return true;
            }
        }
        return false;
    }

    public boolean cancelReservation(int workspaceId, List<workspace> workspaces) {
        for (workspace ws : workspaces) {
            if (ws.getId() == workspaceId) {
                boolean removed = ws.removeReservation(userName);
                if (removed) {
                    userReservations.removeIf(res -> res.getWorkspaceId() == workspaceId);
                    return true;
                }
            }
        }
        return false;
    }

    public List<Reservation> getUserReservations() {
        return new ArrayList<>(userReservations); //Using new  to secure
    }

    public String getUserName() {
        return userName;
    }
}