package com.CoworkingSpace;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorkspaceTest {
    private Workspace workspace;
    private Reservation reservation1;
    private Reservation reservation2;

    @BeforeEach
    void setUp() {
        workspace = new Workspace(1, "Private Office", 50.0f);
        reservation1 = new Reservation("user1",
                LocalDateTime.of(2023, 6, 1, 10, 0),
                LocalDateTime.of(2023, 6, 1, 12, 0),
                1); // Добавлен ID бронирования
        reservation2 = new Reservation("user2",
                LocalDateTime.of(2023, 6, 1, 14, 0),
                LocalDateTime.of(2023, 6, 1, 16, 0),
                2); 
    }

    @Test
    void constructor_ShouldInitializeFieldsCorrectly() {
        assertEquals(1, workspace.getId());
        assertEquals("Private Office", workspace.getType());
        assertEquals(50.0f, workspace.getPrice());
        assertTrue(workspace.getReservations().isEmpty());
    }

    @Test
    void getWorkspaceId_ShouldReturnCorrectId() {
        assertEquals(1, workspace.getWorkspaceId());
    }

    @Test
    void checkAvailability_WithNoReservations_ShouldReturnTrue() {
        LocalDateTime start = LocalDateTime.of(2023, 6, 1, 9, 0);
        LocalDateTime end = LocalDateTime.of(2023, 6, 1, 10, 0);

        assertTrue(workspace.checkAvailability(start, end));
    }

    @Test
    void checkAvailability_WithOverlappingReservation_ShouldReturnFalse() {
        workspace.addReservation(reservation1);

        LocalDateTime start = LocalDateTime.of(2023, 6, 1, 11, 0);
        LocalDateTime end = LocalDateTime.of(2023, 6, 1, 13, 0);

        assertFalse(workspace.checkAvailability(start, end));
    }

    @Test
    void checkAvailability_WithNonOverlappingReservation_ShouldReturnTrue() {
        workspace.addReservation(reservation1);

        LocalDateTime start = LocalDateTime.of(2023, 6, 1, 13, 0);
        LocalDateTime end = LocalDateTime.of(2023, 6, 1, 14, 0);

        assertTrue(workspace.checkAvailability(start, end));
    }

    @Test
    void addReservation_WithValidReservation_ShouldReturnTrue() {
        assertTrue(workspace.addReservation(reservation1));
        assertEquals(1, workspace.getReservations().size());
    }

    @Test
    void addReservation_WithNullReservation_ShouldReturnFalse() {
        assertFalse(workspace.addReservation(null));
        assertTrue(workspace.getReservations().isEmpty());
    }

    @Test
    void removeReservation_WithExistingUser_ShouldReturnTrue() {
        workspace.addReservation(reservation1);
        workspace.addReservation(reservation2);

        assertTrue(workspace.removeReservation("user1"));
        assertEquals(1, workspace.getReservations().size());
    }

    @Test
    void removeReservation_WithNonExistingUser_ShouldReturnFalse() {
        workspace.addReservation(reservation1);

        assertFalse(workspace.removeReservation("unknown_user"));
        assertEquals(1, workspace.getReservations().size());
    }

    @Test
    void getReservations_ShouldReturnCopyOfReservationsList() {
        workspace.addReservation(reservation1);
        List<Reservation> reservations = workspace.getReservations();

        assertEquals(1, reservations.size());


        reservations.add(reservation2);
        assertEquals(1, workspace.getReservations().size());
    }

    @Test
    void edgeCase_ReservationExactlyAtEndTime_ShouldNotOverlap() {
        workspace.addReservation(reservation1);

        LocalDateTime start = reservation1.getEndTime();
        LocalDateTime end = start.plusHours(1);

        assertTrue(workspace.checkAvailability(start, end));
    }

    @Test
    void edgeCase_ReservationExactlyAtStartTime_ShouldOverlap() {
        workspace.addReservation(reservation1);

        LocalDateTime start = reservation1.getStartTime().minusHours(1);
        LocalDateTime end = reservation1.getStartTime();

        assertFalse(workspace.checkAvailability(start, end));
    }
}