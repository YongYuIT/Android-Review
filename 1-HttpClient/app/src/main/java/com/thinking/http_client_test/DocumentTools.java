package com.thinking.http_client_test;

import android.text.TextUtils;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * Created by Yu Yong on 2017/12/25.
 */

public class DocumentTools {
    public static void getALinks(String contents) {
        Document document = Jsoup.parse(contents);
        Elements links = document.select("a[href]");
        for (int i = 0; i < links.size(); i++) {
            Element link = links.get(i);
            String text = link.text();
            if (TextUtils.isEmpty(text.trim())) {
                continue;
            }
            Log.i("yuyong_any", String.format("url:%s;txt:%s", link.attr("href"), link.text()));
        }
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
}
