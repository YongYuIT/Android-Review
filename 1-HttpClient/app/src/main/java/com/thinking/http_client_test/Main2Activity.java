package com.thinking.http_client_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main2Activity extends AppCompatActivity {
    private String[] HttpContent = new String[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_download_baidu) {
            HttpTools.downloadPageAsync("https://www.uuu229.com/htm/index.htm", HttpContent);
        }
        if (v.getId() == R.id.btn_any_baidu) {
            DocumentTools.getALinks(HttpContent[0]);
        }
    }
}
