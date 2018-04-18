package com.auto_ds.app;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class FootballReservationHandler {

    public final static String FOOTBALL_B = "FOOTBALL_B";
    public final static String FOOTBALL_C = "FOOTBALL_C";

    private ConnectionsHandler connHandl;

    private AvailabilityTable table;

    FootballReservationHandler(Session session) throws IOException{
        table = AvailabilityTable.extractTable(session);
        this.connHandl = new ConnectionsHandler(session);
    }

    public boolean reserve(LocalTime time, LocalDate date, String side) throws IOException{
        List<Cell> sideCells;
        switch (side) {
            case FOOTBALL_B:
                sideCells = table.getSideBCells();
                return reserveBasic(time, date, sideCells);
            case FOOTBALL_C:
                sideCells = table.getSideCCells();
                return reserveBasic(time, date, sideCells);
        }
        return false;
    }

    private boolean reserveBasic(LocalTime time, LocalDate date, List<Cell> cells) throws IOException {
        Optional<Cell> validCell = findValidCell(time, date, cells);
        if (validCell.isPresent()) {
            return connHandl.executeGetMethod(validCell.get().getReserveUrl());
        }
        return false;
    }

    private Optional<Cell> findValidCell(LocalTime time, LocalDate date, List<Cell> cells) {
        return cells.stream()
                .filter(c -> c.getDate().equals(date))
                .filter(c -> c.getTime().equals(time))
                .findFirst();
    }

    //TODO MIGHT IMPLEMENT THIS IN FUTURE
    public enum ReserveMethod {
        ALL_BEFORE, ALL_AFTER
    }
}
