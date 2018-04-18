package com.auto_ds.app;

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

public class ConnectionsHandler {

    public static final String DOMAIN = "https://panel.dsnet.agh.edu.pl";
    public static final String RESERV_ENDPOINT = "/reserv/";

    private Session session;

    public ConnectionsHandler(Session session) {
        this.session = session;
    }

    public Document getPageDocument(String url) throws IOException {
        HttpResponse response = executeGetRequest(url);
        String html = getHtmlString(response.getEntity());
        return Jsoup.parse(html);
    }

    public boolean executeGetMethod(String url) throws IOException {
        HttpResponse response = executeGetRequest(url);
        if (response.getStatusLine().getStatusCode() == 302) {
            return true;
        } else
            return false;
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

    private HttpResponse executeGetRequest(String url) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().disableRedirectHandling().build();
        HttpGet getRequest = new HttpGet(url);
        HeaderUtils.supplyWithDefaultHeaders(getRequest);
        HeaderUtils.supplyWithHeaders(getRequest, session.getSessionHeader());
        return httpClient.execute(getRequest);
    }
}
