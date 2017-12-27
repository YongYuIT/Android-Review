package com.thinking.http_client_test;

import android.text.TextUtils;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yu Yong on 2017/12/25.
 */

public class DocumentTools {
    public static List<String> getALinks(String contents) {
        List<String> results = new ArrayList<>();
        Document document = Jsoup.parse(contents);
        Elements links = document.select("a[href]");
        for (int i = 0; i < links.size(); i++) {
            Element link = links.get(i);
            String text = link.text();
            if (TextUtils.isEmpty(text.trim())) {
                continue;
            }
            String url = link.attr("href");
            results.add(url);
            Log.i("yuyong_any", String.format("url:%s;txt:%s", url, text));
        }
        return results;
    }

    public static void getGetAudio(String contents, List output) {
        Document document = Jsoup.parse(contents);
        Elements audios = document.select("audio");
        for (int i = 0; i < audios.size(); i++) {
            Element audio = audios.get(i);
            String src = audio.attr("src");
            output.add(src);
            Log.i("yuyong_any", String.format("url:%s;", src));
        }
    }

    public static String joinUrl(String url_1, String url_2) {
        String tmp = url_1.replace("//", "##");
        int root_index = tmp.indexOf("/");
        String root = url_1.substring(0, root_index);
        return root + url_2;
    }
}
