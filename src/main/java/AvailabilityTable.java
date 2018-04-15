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

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class AvailabilityTable {

    private ConnectionsHandler connHandl;
    private SessionManager sessionManager;
    private List<Cell> sideB;
    private List<Cell> sideC;
    private String sideBURL;
    private String sideCURL;

    private AvailabilityTable(SessionManager sessionManager) {
        this.connHandl = new ConnectionsHandler(sessionManager);
        this.sessionManager = sessionManager;
        setUpUrls();
        setUpMonthMap();
    }

    public static AvailabilityTable extractTable(SessionManager sessionManager) {
        return new AvailabilityTable(sessionManager);
    }

    public List<Cell> getSideBCells() {
        if (sideB == null) {
            sideB = parseCells(sideBURL);
        }
        return sideB;
    }

    public List<Cell> getSideCCells() {
        if (sideC == null) {
            sideC = parseCells(sideCURL);
        }
        return sideC;
    }

    private List<Cell> parseCells(String url) {
        List<Cell> cellsList = new LinkedList<>();
        try {
            Document page = connHandl.getPageDocument(url);
            List<LocalDate> dates = parseDates(page.select("thead > tr > th").select("[style=width: 33%]"));
            Elements rows = page.select("tbody > tr");
            for (Element row : rows) {
                    LocalTime timeAt = parseHour(row.selectFirst("th"));
                    Elements cells = row.select("td");
                    for (int i = 0; i < cells.size(); i++) {
                        String href = cells.get(i).select("a").attr("href");
                        Cell c;
                        if (!href.isEmpty()) {
                            c = new Cell(timeAt, dates.get(i), true, ConnectionsHandler.DOMAIN + href);

                        } else {
                            c = new Cell(timeAt, dates.get(i), false, "-");

                        }
                        cellsList.add(c);
                }
            }

        } catch (IOException e) {
            System.err.println("ERROR WHILE PARSING CELLS \n");
            e.printStackTrace();
            throw new RuntimeException();
        }
        return cellsList;
    }

    private void setUpUrls() {
        try {
            Document page = connHandl.getPageDocument(ConnectionsHandler.DOMAIN + ConnectionsHandler.RESERV_ENDPOINT);
            sideBURL = ConnectionsHandler.DOMAIN + extractSideTableURL(page, "Część \"B\"");
            sideCURL = ConnectionsHandler.DOMAIN + extractSideTableURL(page, "Część \"C\"");
        } catch (IOException e) {
            System.err.println("ERROR WHILE EXTRACTING RESERVATION URLS \n");
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private String extractSideTableURL(Document document, String side) {
        return document.getElementsContainingText(side).attr("href");
    }

    private List<LocalDate> parseDates(Elements dates) {
        List<LocalDate> localDates = new ArrayList<>();
        for (Element date : dates) {
            String[] dateTable = date.text().split(" ");
            localDates.add(LocalDate
                    .of(Integer.valueOf(dateTable[3]), mnthMap.get(dateTable[2]), Integer.valueOf(dateTable[1])));
        }
        return localDates;
    }

    private LocalTime parseHour(Element html) {
        String hourString = html.text().split(" ")[0];
        return LocalTime.parse(hourString);
    }

    //TODO Should move this somewhere else in future
    private Map<String, Month> mnthMap = new HashMap<>();

    private void setUpMonthMap() {
        mnthMap.put("sty", Month.JANUARY);
        mnthMap.put("lut", Month.FEBRUARY);
        mnthMap.put("mar", Month.MARCH);
        mnthMap.put("kwi", Month.APRIL);
        mnthMap.put("maj", Month.MAY);

        //TODO ADD MORE....
    }

}