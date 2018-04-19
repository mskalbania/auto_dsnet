package com.auto_ds.app;

import com.auto_ds.handler.ConnectionsHandler;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public class FootballReservationHandler implements ReservationHandler {

    public final static String FOOTBALL_B = "FOOTBALL_B";
    public final static String FOOTBALL_C = "FOOTBALL_C";

    private ConnectionsHandler connHandl;

    private AvailabilityTable table;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public FootballReservationHandler(Session session) throws IOException {
        table = AvailabilityTable.extractTable(session);
        this.connHandl = new ConnectionsHandler(session);
    }

    public boolean reserve(LocalTime time, LocalDate date, String side) throws IOException {
        List<Cell> sideCells;
        switch (side) {
            case FOOTBALL_B:
                sideCells = table.getSideBCells();
                logger.info("FOUND CELLS : " + sideCells);
                return reserveBasic(time, date, sideCells);
            case FOOTBALL_C:
                sideCells = table.getSideCCells();
                logger.info("FOUND CELLS : " + sideCells);
                return reserveBasic(time, date, sideCells);
        }
        return false;
    }

    private boolean reserveBasic(LocalTime time, LocalDate date, List<Cell> cells) throws IOException {
        Optional<Cell> validCell = findValidCell(time, date, cells);
        if (validCell.isPresent()) {
            logger.info("FOUND VALID CELL: " + validCell.get());
            HttpResponse response = connHandl.executeGetRequest(validCell.get().getReserveUrl(), true);
            logger.info("RESPONSE STATUS CODE: " + response.getStatusLine().getStatusCode());
            return response.getStatusLine().getStatusCode() == 302; //add validation here
        }
        logger.info("VALID CELL NOT FOUND");
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
