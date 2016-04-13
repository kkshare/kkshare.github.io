package com.eben4a.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class HttpHelper {
    public static String getData(String urlstr) {
        HttpURLConnection connection = null;
        BufferedReader in = null;
        URL url = null;
        URLConnection urlConnection = null;
        String current = null;
        StringBuffer resultStr = null;
        int returyCount = 0;
        while (true) {
            try {
                url = new URL(urlstr);
                urlConnection = url.openConnection();
                if (urlConnection instanceof HttpURLConnection) {
                    connection = (HttpURLConnection) urlConnection;
                    connection.setConnectTimeout(3000);
                    connection.setReadTimeout(3000);
                    in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    resultStr = new StringBuffer();
                    while ((current = in.readLine()) != null) {
                        resultStr.append(current);
                    }
                    break;
                } else {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
                if(returyCount < 3){//(e instanceof SocketTimeoutException)
                    returyCount ++;
                    pause(returyCount);
                } else {
                    e.printStackTrace();
                    break;
                }
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }
        return resultStr.toString();
    }
    private static void pause(int retries) {
        // make the pause time increase exponentially
        // based on an assumption that the more times it retries,
        // the less probability it succeeds.
        int scale = 300;
        long delay = (long) Math.pow(2, retries) * scale;

        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

