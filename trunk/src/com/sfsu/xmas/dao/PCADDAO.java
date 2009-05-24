/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sfsu.xmas.dao;

import com.sfsu.xmas.monitoring.ExecutionTimer;
import java.util.logging.Level;
import net.htmlparser.jericho.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 *
 * @author bdalziel
 */
public class PCADDAO {

    public static final String CONNECT_TIMEOUT = "connection timeout";
    public static final String READ_TIMEOUT = "read timeout";
    public static final int connectionTimeout = 3000;
    public static final int readTimeout = 5000;
    private static PCADDAO instance;
    private boolean isLoggedIn = false;

    private PCADDAO() {
        checkHandler();
        loginToPCAD();
    }

    public synchronized static PCADDAO getInstance() {
        if (instance == null) {
            instance = new PCADDAO();
        }
        return instance;
    }

    private synchronized int loginToPCAD() {
        int responseCode = 0;
        if (!isLoggedIn) {
            try {
                URL u = new URL("http://array.sfsu.edu/cgi-bin/SMD/login.pl");
                HttpURLConnection conn = (HttpURLConnection) u.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setInstanceFollowRedirects(false);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                conn.setReadTimeout(readTimeout);

                conn.setConnectTimeout(connectionTimeout);
                try {
                    conn.connect();
                } catch (java.net.SocketTimeoutException ex) {
                    System.err.println(this.getClass().getSimpleName() + ": Connection timeout");
                }
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write("user=" + URLEncoder.encode("bdalziel", "UTF-8") + "&pass=" + URLEncoder.encode("@bdalzie", "UTF-8"));
                wr.flush();

                BufferedReader buff = new BufferedReader(new InputStreamReader((InputStream) conn.getContent()));
//        out.write("Getting data ...");
                String line = buff.readLine();
                while (line != null) {
                    System.out.println(line + "\n");
                    line = buff.readLine();
                }
                conn.disconnect();
                responseCode = conn.getResponseCode();
                isLoggedIn = true;
            } catch (java.net.UnknownHostException ex) {
                System.err.println(this.getClass().getSimpleName() + ": Connect Error");
//            java.util.logging.Logger.getLogger(PCADDAO.class.getName()).log(Level.SEVERE, null, ex);
                isLoggedIn = false;
            } catch (MalformedURLException ex) {
                java.util.logging.Logger.getLogger(PCADDAO.class.getName()).log(Level.SEVERE, null, ex);
                isLoggedIn = false;
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(PCADDAO.class.getName()).log(Level.SEVERE, null, ex);
                isLoggedIn = false;
            }
        }
        return responseCode;
    }

    public StringBuffer makePCADProbeQuery(String probeID) {
        ExecutionTimer et = new ExecutionTimer();
        et.start();

        StringBuffer sb = new StringBuffer();

        try {
            URL u = new URL("http://array.sfsu.edu/cgi-bin/SMD/pcad_result_top.pl");
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

//        conn.addRequestProperty("submit_query", "do_submit");
//        conn.addRequestProperty("submit_query_list", "false");
//        conn.addRequestProperty("EST_CAYC_CAYF_CCAG", "CCAG2670");
//        conn.addRequestProperty("pcad_evaluation", "0");
//        conn.addRequestProperty("form_file_name", "");

            conn.setReadTimeout(readTimeout);

            conn.setConnectTimeout(connectionTimeout);
            try {
                conn.connect();
            } catch (java.net.SocketTimeoutException ex) {
                System.err.println(this.getClass().getSimpleName() + ": Connection timeout");
                sb.append(CONNECT_TIMEOUT);
                return sb;
            }

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write("submit_query=" + URLEncoder.encode("do_submit", "UTF-8") +
                    "&submit_query_list" + URLEncoder.encode("false", "UTF-8") +
                    "&EST_CAYC_CAYF_CCAG=" + URLEncoder.encode(probeID, "UTF-8") +
                    "&pcad_evaluation=" + URLEncoder.encode("0", "UTF-8") +
                    "&form_file_name=" + URLEncoder.encode("", "UTF-8"));
            wr.flush();

            MasonTagTypes.register();
            Source source = new Source((InputStream) conn.getContent());

            // Call fullSequentialParse manually as most of the source will be parsed.
            source.fullSequentialParse();

            String probeDescription = getInputValue(source, "name", "PCAD_Summary");
//        System.out.println(probeDescription == null ? "(none)" : probeDescription);

            sb.append(probeDescription);

            conn.disconnect();

        } catch (java.net.SocketTimeoutException ex) {
            System.err.println(this.getClass().getSimpleName() + ": Read timeout");
            sb.append(READ_TIMEOUT);
        } catch (java.net.UnknownHostException ex) {
            System.err.println(this.getClass().getSimpleName() + ": Connect Error");
//            java.util.logging.Logger.getLogger(PCADDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            java.util.logging.Logger.getLogger(PCADDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(PCADDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        et.end();

        System.out.println(this.getClass().getSimpleName() + ": DURATION = \"" + et.duration() + "\" for probe ID = \"" + probeID + "\"");

        return sb;
    }

    private static String getInputValue(Source source, String attributeName, String attributeValue) {
//        Element titleElement = source.getNextElement(pos, HTMLElementName.INPUT);
        for (int pos = 0; pos < source.length();) {
            Element startTag = source.getNextElement(pos, HTMLElementName.INPUT);
            if (startTag == null) {
                return null;
            }

            if (startTag.getAttributeValue(attributeName).equals(attributeValue)) {
                return startTag.getAttributeValue("value");
            }
            pos = startTag.getEnd();
        }
        return null;
    }

    private boolean checkHandler() {
        CookieHandler handler = CookieHandler.getDefault();
        if (handler == null) {
            CookieManager cm = new CookieManager();
            CookieHandler.setDefault(cm);
            cm.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        }
        return CookieHandler.getDefault() != null;
    }
}
