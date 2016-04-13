package cn.eben4a.test;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;

/**
* HttpClient是个很不错的开源框架，封装了访问http的请求头，参数，内容体，响应等等
* HttpURLConnection是java的标准类，什么都没封装，用起来太原始，不方便，比如重访问的自定义，以及一些高级功能等
* @author hetq
*/
public class HttpRequest2 {

    private static String host = "http://42.121.96.124/4a";

    public static Resp doGet(String uri, Map params) {
        Resp resp = new Resp();
        StringBuffer queryStr = new StringBuffer();
        Iterator<Entry> it = params.entrySet().iterator();
        int n = params.size();
        queryStr.append('?');
        while(it.hasNext()){
            n--;
            Entry entry = it.next();
            queryStr.append(entry.getKey()).append('=').append(entry.getValue());
            if(n>0)
                queryStr.append('&');
        }
        String getUrl = host + uri + queryStr.toString();
        HttpGet httpGet = new HttpGet(getUrl);
        HttpParams hp = httpGet.getParams();
        hp.getParameter("true");
        HttpClient hc = new DefaultHttpClient();
        try {
            HttpResponse ht = hc.execute(httpGet);
            resp.code = ht.getStatusLine().getStatusCode();
            if (resp.code == HttpStatus.SC_OK) {
                HttpEntity he = ht.getEntity();
                InputStream is = he.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuffer resultStr = new StringBuffer();
                String readLine = null;
                while ((readLine = br.readLine()) != null) {
                    resultStr.append(readLine);
                }
                is.close();
                br.close();
                resp.body = resultStr.toString();
            }
            return resp;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Resp doPost(String uri, Map params) {
        Resp resp = new Resp();
        HttpPost httpPost = new HttpPost(host+uri);
        List pList = new ArrayList();
        Iterator<Entry> it = params.entrySet().iterator();
        while(it.hasNext()){
            Entry entry = it.next();
            NameValuePair pair = new BasicNameValuePair((String)entry.getKey(), (String)entry.getValue());
            pList.add(pair);
        }

        HttpEntity he;
        try {
            he = new UrlEncodedFormEntity(pList, "gbk");
            httpPost.setEntity(he);
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
            return null;
        }

        HttpClient hc = new DefaultHttpClient();
        try {
            HttpResponse ht = hc.execute(httpPost);
            //连接成功
            resp.code = ht.getStatusLine().getStatusCode();
            if (resp.code == HttpStatus.SC_OK) {
                HttpEntity het = ht.getEntity();
                InputStream is = het.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuffer resultStr = new StringBuffer();
                String readLine = null;
                while ((readLine = br.readLine()) != null) {
                    resultStr.append(readLine);
                }
                is.close();
                br.close();
                resp.body = resultStr.toString();
            }
            return resp;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

