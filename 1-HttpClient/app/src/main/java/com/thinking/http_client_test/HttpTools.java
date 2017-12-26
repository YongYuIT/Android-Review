package com.thinking.http_client_test;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.CloseableHttpClient;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;
import cz.msebera.android.httpclient.util.EntityUtils;

/**
 * Created by Yu Yong on 2017/12/25.
 */

public class HttpTools {
    //http://hc.apache.org/downloads.cgi
    public static String downloadPage(String path) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(path);
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            String html = EntityUtils.toString(entity, getCharset(response));
            EntityUtils.consume(entity);
            return html;
        }
        return null;
    }

    public static String getCharset(HttpResponse response) {
        Pattern pattern = Pattern.compile("text/html;[\\s]*charset=(.*)");
        Header[] test = response.getAllHeaders();
        Header[] arrs = response.getHeaders("Content-Type");
        String charset = "utf-8";
        if (arrs != null && arrs.length > 0) {
            String content = arrs[0].getValue().toLowerCase();
            Matcher m = pattern.matcher(content);
            if (m.find()) {
                charset = m.group(1);
            }
        }
        return charset;
    }

    public static void downloadPageAsync(String path, final String[] output) {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                try {
                    output[0] = downloadPage(strings[0]);
                    return output[0];
                } catch (Exception e) {
                    return e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String s) {
                Log.i("yuyong", s);
            }
        }.execute(path);
    }
}
