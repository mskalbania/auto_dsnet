/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2017
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/

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
}
