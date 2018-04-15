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

import java.io.IOException;
import java.util.List;

public class AvailabilityTable {

	private ConnectionsHandler connHandl;
	private SessionManager sessionManager;
	private List<Cell> sideB;
	private List<Cell> sideC;
	private String sideBURL;
	private String sideCURL;

	private AvailabilityTable(SessionManager sessionManager) {
		this.connHandl = new ConnectionsHandler(sessionManager, false);
		this.sessionManager = sessionManager;
		setUpUrls();
	}

	public static AvailabilityTable extractTable(SessionManager sessionManager) {
		return new AvailabilityTable(sessionManager);
	}

	public List<Cell> getSideBCells() {
		if (sideB == null) {
			parseCells(sideBURL);
		}
		return sideB;
	}

	public List<Cell> getSideCCells() {
		if (sideC == null) {
			parseCells(sideCURL);
		}
		return sideC;
	}

	private void parseCells(String url) {
		try {
			sessionManager.openSession();
			Document page = connHandl.getPageDocument(url);
			System.out.println(page.toString());


			System.out.println();
		} catch (IOException e) {
			System.err.println("ERROR WHILE PARSING CELLS \n");
			e.printStackTrace();
			throw new RuntimeException();
		}

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
}