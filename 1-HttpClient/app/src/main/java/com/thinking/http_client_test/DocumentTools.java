package com.thinking.http_client_test;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Yu Yong on 2017/12/25.
 */

public class DocumentTools {
    public static void getALinks(String contents) {
        Document document = Jsoup.parse(contents);
        Elements links = document.select("a[href]");
        for (int i = 0; i < links.size(); i++) {
            Element link = links.get(i);
            Log.i("yuyong_any", String.format("url:%s;txt:%s", link.attr("href"), link.text()));
        }
    }
}
