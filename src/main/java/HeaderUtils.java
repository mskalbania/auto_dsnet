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

import org.apache.http.HttpMessage;

public class HeaderUtils {

    //HEADER KEYS
    public static final String ACCEPT = "Accept";
    public static final String ACCEPT_ENCODING = "Accept-Encoding";
    public static final String ACCEPT_LANGUAGE = "Accept-Language";
    public static final String UPGRD_INS_REQ = "Upgrade-Insecure-Requests";
    public static final String USER_AGENT = "User-Agent";
    public static final String CONTENT_TYPE = "Content-Type";
    //HEADER VALUES
    public static final String TEXT_HTML = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8";
    public static final String DEFAULT_ENCODINGS = "gzip, deflate, br";
    public static final String LANGUAGES = "pl-PL,pl;q=0.9,en-US;q=0.8,en;q=0.7";
    public static final String DEFAULT_AGENT = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36";
    public static final String DEFAULT_CONT_TYPE = "application/x-www-form-urlencoded";

    public static void supplyWithDefaultHeaders(HttpMessage message) {
        message.addHeader(ACCEPT, TEXT_HTML);
        message.addHeader(ACCEPT_ENCODING, DEFAULT_ENCODINGS);
        message.addHeader(ACCEPT_LANGUAGE, LANGUAGES);
        message.addHeader(UPGRD_INS_REQ, "1");
        message.addHeader(USER_AGENT, DEFAULT_AGENT);
        message.addHeader(CONTENT_TYPE, DEFAULT_CONT_TYPE);
    }
}
