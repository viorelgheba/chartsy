package org.chartsy.main.utils;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.chartsy.main.managers.ProxyManager;

/**
 *
 * @author viorelgheba
 */
public class URLChecker {

    private URLChecker() {}

    public static boolean checkURL(String url) {
        boolean ok = false;
        try {
            HttpClient httpClient = ProxyManager.getDefault().getHttpClient();
            HttpMethod method = new GetMethod(url);

            int responce = httpClient.executeMethod(method);
            if (responce == HttpStatus.SC_OK) ok = true;
            method.releaseConnection();
        } catch (Exception ex) {
            ok = false;
            ex.printStackTrace();
        }
        return ok;
    }

    public static boolean checkURL(URL url) {
        boolean ok = false;
        try {
            HttpClient httpClient = ProxyManager.getDefault().getHttpClient();
            HttpMethod method = new GetMethod(url.toString());

            int responce = httpClient.executeMethod(method);
            if (responce == HttpStatus.SC_OK) ok = true;
            method.releaseConnection();
        } catch (Exception ex) {
            ok = false;
            ex.printStackTrace();
        }
        return ok;
    }

    public static boolean isInternetReachable()
    {
        try
        {
            HttpClient client = ProxyManager.getDefault().getHttpClient();
            GetMethod method = new GetMethod("http://www.google.com");
            int responce = client.executeMethod(method);
            System.out.println(HttpStatus.getStatusText(responce));
            method.releaseConnection();
        }
        catch (UnknownHostException ex)
        {
            ex.printStackTrace();
            return false;
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

}
