package com.CoworkingSpace;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Workspace implements Serializable {
    private final int id;
    private final String type;
    private final float price;
    private final List<Reservation> reservations; //List to store reservations connected to a current workspace

    public Workspace(int id, String type, float price) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.reservations = new ArrayList<>();
    }

    public int getWorkspaceId() {
        return id;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public float getPrice() {
        return price;
    }

    public boolean checkAvailability(LocalDateTime startTime, LocalDateTime endTime) {  //This method checks availability of workspace
        for (Reservation res : reservations) {
            if (startTime.isBefore(res.getEndTime()) && endTime.isAfter(res.getStartTime())) {
                return false;
            }
        }
        return true;
    }

    public boolean addReservation(Reservation reservation) { //Adding new reservation
        if (reservation != null) {
            return reservations.add(reservation);
        } else{
            System.out.println("Reservation can't be null");
        }
        return false;

    }

    public boolean removeReservation(String userName) {
        return reservations.removeIf(res -> res.getUsername().equals(userName));  //Deleting all the reservations connected to user
    }

    public List<Reservation> getReservations() {
        return new ArrayList<>(reservations);
    }
}