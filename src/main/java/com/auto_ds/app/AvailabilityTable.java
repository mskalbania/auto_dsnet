package com.auto_ds.app;

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
    private List<Cell> sideB;
    private List<Cell> sideC;
    private String sideBURL;
    private String sideCURL;

    private AvailabilityTable(Session session) throws IOException {
        this.connHandl = new ConnectionsHandler(session);
        setUpUrls();
        setUpMonthMap();
    }

    public static AvailabilityTable extractTable(Session session) throws IOException {
        return new AvailabilityTable(session);
    }

    public List<Cell> getSideBCells() throws IOException {
        sideB = parseCells(sideBURL);
        return sideB;
    }

    public List<Cell> getSideCCells() throws IOException {
        sideC = parseCells(sideCURL);
        return sideC;
    }

    private List<Cell> parseCells(String url) throws IOException {
        List<Cell> cellsList = new LinkedList<>();

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
        return cellsList;
    }

    private void setUpUrls() throws IOException {
        Document page = connHandl.getPageDocument(ConnectionsHandler.DOMAIN + ConnectionsHandler.RESERV_ENDPOINT);
        sideBURL = ConnectionsHandler.DOMAIN + extractSideTableURL(page, "Część \"B\"");
        sideCURL = ConnectionsHandler.DOMAIN + extractSideTableURL(page, "Część \"C\"");
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