import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConnectionsHandler {

	public static final String DOMAIN = "https://panel.dsnet.agh.edu.pl";
	public static final String RESERV_ENDPOINT = "/reserv/";

	private SessionManager sessionManager;

	public ConnectionsHandler(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	public Document getPageDocument(String url) throws IOException {
		HttpClient httpClient = HttpClientBuilder.create().disableRedirectHandling().build();

		HttpGet getRequest = new HttpGet(url);
		HeaderUtils.supplyWithDefaultHeaders(getRequest);
		HeaderUtils.supplyWithHeaders(getRequest, sessionManager.getSessionHeader());

		HttpResponse response = httpClient.execute(getRequest);
		String html = getHtmlString(response.getEntity());
		return Jsoup.parse(html);
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