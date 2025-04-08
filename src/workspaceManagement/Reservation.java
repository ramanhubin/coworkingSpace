package workspaceManagement;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Reservation implements Serializable {
    private final String userName;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final int workspaceId;

    public Reservation(String userName, LocalDateTime startTime, LocalDateTime endTime, int workspaceId) {
        this.userName = userName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.workspaceId = workspaceId;
    }

    public String getUsername() {
        return userName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public int getWorkspaceId() {
        return workspaceId;
    }
}