package com.auto_ds.app;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.springframework.stereotype.Component;

public class Session {

    private final String LOGIN_URL = "https://panel.dsnet.agh.edu.pl/login_check";

    private Header sessionHeader;
    private HttpClient client;

    private Session(String email, String password) {
        setUpSession(email, password);
    }

    public static Session openSession(String email, String password) {
        return new Session(email, password);
    }

    public Header getSessionHeader() {
        return this.sessionHeader;
    }

    private void setUpSession(String email, String password) {
        client = HttpClientBuilder.create().disableRedirectHandling().build();

        HttpPost request = new HttpPost(LOGIN_URL);
        HeaderUtils.supplyWithDefaultHeaders(request);
        String postParams = preparePostParams(email, password);
        request.setEntity(EntityBuilder.create().setBinary(postParams.getBytes()).build());

        try {
            HttpResponse response = client.execute(request);
            Header header = response.getHeaders("Set-Cookie")[0];
            this.sessionHeader = new BasicHeader("Cookie", header.getValue());
        } catch (Exception e) {
            System.out.println("ERROR WHILE SETTING UP SESSION");
            e.printStackTrace();
        }
    }

    private String preparePostParams(String email, String password) {
        String[] emailSplit = email.split("@");
        return "_username=" + emailSplit[0] + "%40" + emailSplit[1] + "&_password=" + password;
    }
}
