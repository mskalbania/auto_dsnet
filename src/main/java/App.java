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

import com.sun.webkit.network.CookieManager;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

import java.net.CookieHandler;

public class App {

    private static final String MAIN_URL = "https://panel.dsnet.agh.edu.pl/";
    private static final String LOGIN_URL = "https://panel.dsnet.agh.edu.pl/login_check";
    private static final String EMAIL = "cod634.steam@gmail.com";
    private static final String PASSWORD = "Guccio11guccio2";

    private Header defaultSessionHeder;

    public static void main(String[] args) throws Exception {

        App app = new App();
        CookieHandler.setDefault(new CookieManager());
//        app.configureDefaultSessionCookie();
        Header sessionHeader = app.logInUser();
    }

    private void configureDefaultSessionCookie() throws Exception {

        HttpClient client = HttpClientBuilder.create().disableRedirectHandling().build();
        HttpGet request = new HttpGet(MAIN_URL);

        request.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        request.addHeader("Accept-Encoding", "gzip, deflate, br");
        request.addHeader("Accept-Language", "pl-PL,pl;q=0.9,en-US;q=0.8,en;q=0.7");
        request.addHeader("Upgrade-Insecure-Requests", "1");
        request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");

        HttpResponse response = client.execute(request);

        defaultSessionHeder = response.getHeaders("Set-Cookie")[0];
    }

    private Header logInUser() throws Exception {

        HttpClient client = HttpClientBuilder.create().disableRedirectHandling().build();
        HttpPost request = new HttpPost(LOGIN_URL);

        request.addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        request.addHeader("Accept-Encoding", "gzip, deflate, br");
        request.addHeader("Accept-Language", "pl-PL,pl;q=0.9,en-US;q=0.8,en;q=0.7");
        request.addHeader("Upgrade-Insecure-Requests", "1");
        request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36");
        request.addHeader("Content-Type", "application/x-www-form-urlencoded");
        String postParams = preparePostParams();
        request.addHeader(defaultSessionHeder);
        request.setEntity(EntityBuilder.create().setBinary(postParams.getBytes()).build());
        HttpResponse response = client.execute(request);

        return response.getHeaders("Set-Cookie")[0];
    }

    void testReservation(Header header) {}

    private String preparePostParams() {
        String[] emailSplit = EMAIL.split("@");
        return "_username=" + emailSplit[0] + "%40" + emailSplit[1] + "&_password=" + PASSWORD;
    }
}
