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

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

public class Session {

    private final String LOGIN_URL = "https://panel.dsnet.agh.edu.pl/login_check";

    private Header sessionHeader;
    private HttpClient client;

    private Session(String email, String password) {
        setupSessionHeader(email, password);
    }

    public static Session openSession(final String email, final String password) {
        return new Session(email, password);
    }

    public Header getSessionHeader() {
        return this.sessionHeader;
    }

    private void setupSessionHeader(final String email, final String password) {
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

    private String preparePostParams(final String email, final String password) {
        String[] emailSplit = email.split("@");
        return "_username=" + emailSplit[0] + "%40" + emailSplit[1] + "&_password=" + password;
    }
}
