package cn.eben4a.test;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Map.Entry;

/**
* HttpClient是个很不错的开源框架，封装了访问http的请求头，参数，内容体，响应等等
* HttpURLConnection是java的标准类，什么都没封装，用起来太原始，不方便，比如重访问的自定义，以及一些高级功能等
* @author hetq
*/
public class HttpRequest1 {

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
            String value = null;
            try{//解决中文编码问题
                value = URLEncoder.encode((String)entry.getValue(),"UTF-8");
            }catch(Exception e){}
            queryStr.append(entry.getKey()).append('=').append(value);
            if(n>0)
                queryStr.append('&');
        }
        String getUrl = host + uri + queryStr.toString();
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        try {
            URL url = new URL(getUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(3000);
            connection.setReadTimeout(3000);
            resp.code = connection.getResponseCode();
            resp.message = connection.getResponseMessage();
            if(resp.code == 200){
                is = connection.getInputStream();
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuffer resultStr = new StringBuffer();
                String readLine = null;
                while ((readLine = br.readLine()) != null) {
                    resultStr.append(readLine);
                }
                resp.body = resultStr.toString();
            }
            return resp;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally{
            if(is!=null){
                try{is.close();}catch(Exception e){}
            }
            if(br!=null){
                try{br.close();}catch(Exception e){}
            }
            if(connection!=null){
                try{connection.disconnect();}catch(Exception e){}
            }
        }
    }

    public static Resp doPost(String uri, Map params) {
        Resp resp = new Resp();
        StringBuffer content = new StringBuffer();
        Iterator<Entry> it = params.entrySet().iterator();
        int n = params.size();
        while(it.hasNext()){
            n--;
            Entry entry = it.next();
            String value = null;
            try{//解决中文编码问题
                value = URLEncoder.encode((String)entry.getValue(),"UTF-8");
            }catch(Exception e){}
            content.append(entry.getKey()).append('=').append(value);
            if(n>0)
                content.append('&');
        }
        System.out.println("curl -i -X POST "+host+uri+" -d '"+content.toString()+"'");
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        try {
            URL url = new URL(host+uri);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(content.toString());
            out.flush();
            out.close();

            resp.code = connection.getResponseCode();
            resp.message = connection.getResponseMessage();
            if(resp.code == 200){
                StringBuffer resultStr = new StringBuffer();
                is = connection.getInputStream();
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String readLine = null;
                while ((readLine = br.readLine()) != null) {
                    resultStr.append(readLine);
                }
                resp.body = resultStr.toString();
            }
            return resp;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally{
            if(is!=null){
                try{is.close();}catch(Exception e){}
            }
            if(br!=null){
                try{br.close();}catch(Exception e){}
            }
            if(connection!=null){
                try{connection.disconnect();}catch(Exception e){}
            }
        }
    }
}
