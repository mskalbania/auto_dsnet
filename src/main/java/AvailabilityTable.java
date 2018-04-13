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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class AvailabilityTable {

    private static final String RESERVATIONS_URL = "https://panel.dsnet.agh.edu.pl/reserv/";

    private static String sideBURL;
    private static String sideCURL;

    private Session session;
    private List<Cell> sideA;
    private List<Cell> sideB;

    private AvailabilityTable(Session session) {
        this.session = session;
        sideBURL = extractSideTableURL("Część B");
        sideCURL = extractSideTableURL("Część C");
    }

    public static AvailabilityTable extractTable(Session session) {
        return new AvailabilityTable(session);
    }


    private String extractSideTableURL(String regex) {
        HttpClient client = HttpClientBuilder.create().disableRedirectHandling().build();
        HttpGet request = new HttpGet(RESERVATIONS_URL);

        HeaderUtils.supplyWithDefaultHeaders(request);
        request.addHeader(session.getSessionHeader());

        try {
            HttpResponse response = client.execute(request);

            HttpEntity entity = response.getEntity();

            String htmlSite = "";

            if (entity != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
                String line = "";
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                htmlSite = sb.toString();
            }

            Document fullPage = Jsoup.parse(htmlSite);


        } catch (Exception e) {
            System.out.println("ERROR WHILE GETTING RESERVATIONS PAGE");
            e.printStackTrace();
        }
        return "";
    }
}
