package com.auto_ds.app;

import java.time.LocalDate;
import java.time.LocalTime;

public class Cell {

    private LocalTime time;
    private LocalDate date;
    private boolean isAvailableToReserve;
    private String reserveUrl;

    public Cell(LocalTime time, LocalDate date, boolean isAvailableToReserve, String reserveUrl) {
        this.time = time;
        this.date = date;
        this.isAvailableToReserve = isAvailableToReserve;
        this.reserveUrl = reserveUrl;
    }

    public LocalTime getTime() {
        return time;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isAvailableToReserve() {
        return isAvailableToReserve;
    }

    public String getReserveUrl() {
        return reserveUrl;
    }

    @Override
    public String toString() {
        return "D: " + date.toString() + " T: " + time.toString() + " AV: " + isAvailableToReserve + "\n";
    }
}
