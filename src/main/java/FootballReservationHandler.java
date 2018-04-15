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

public class FootballReservationHandler {

    private AvailabilityTable table;

    FootballReservationHandler(SessionManager sessionManager) {
        table = AvailabilityTable.extractTable(sessionManager);
    }

    public boolean reserve(LocalTime time, LocalDate date, ReserveMethod reserveMethod) {
        table.getSideCCells();

        //TODO
        return false;
    }

    public enum ReserveMethod {
        ALL_BEFORE, ALL_AFTER
    }
}
