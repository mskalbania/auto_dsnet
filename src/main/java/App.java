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

public class App {

    public static void main(String[] args) {


        Session session = Session.openSession("cod634.steam@gmail.com", "Guccio11guccio2");
        FootballReservationHandler frh = new FootballReservationHandler(session);

    }
}
