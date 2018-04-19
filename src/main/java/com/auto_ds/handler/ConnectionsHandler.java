package com.auto_ds.handler;

import com.auto_ds.app.HeaderUtils;
import com.auto_ds.app.Session;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class ConnectionsHandler {

    public static final String DS_DOMAIN = "https://panel.dsnet.agh.edu.pl";
    public static final String RESERV_ENDPOINT = "/reserv/";

    public static final String HEROKU_DOMAIN = "https://auto-ds.herokuapp.com/";
    public static final String SHUT_DOWN_ENDPOINT = "/shutDown";
    public static final String KEEP_ALIVE_ENDPOINT = "/keepAlive";
    public static final String FAKE_ENDPOINT = "/fakeEndPoint";
    public static final String HELP_ENDPOINT = "/help";
    public static final String FOOTBALL_RESERVE_ENDPOINT = "/footballReserve";
    public static final String RESERVATION_LIST_ENDPOINT = "/reservationList";
    public static final String CLEAR_RESERVATIONS_ENDPOINT = "/clearReservations";
    public static final String CURR_TIME_ENDPOINT = "/currentTime";

    private Session session;

    public ConnectionsHandler() {
    }

    public ConnectionsHandler(Session session) {
        this.session = session;
    }

    public Document getPageDocument(String url) throws IOException {
        HttpResponse response = executeGetRequest(url, true);
        String html = getHtmlString(response.getEntity());
        return Jsoup.parse(html);
    }

    public HttpResponse executeGetRequest(String url, boolean defaultHeadersRequested) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().disableRedirectHandling().build();
        HttpGet getRequest = new HttpGet(url);
        if (defaultHeadersRequested && session != null) {
            HeaderUtils.supplyWithDefaultHeaders(getRequest);
            HeaderUtils.supplyWithHeaders(getRequest, session.getSessionHeader());
        }
        return httpClient.execute(getRequest);
    }

    private String getHtmlString(HttpEntity entity) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent()));
        String current;
        while ((current = br.readLine()) != null) {
            sb.append(current);
        }
        return sb.toString();
    }


}
