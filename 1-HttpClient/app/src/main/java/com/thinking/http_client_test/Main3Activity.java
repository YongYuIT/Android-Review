package com.thinking.http_client_test;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main3Activity extends AppCompatActivity {

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1001) {
                Toast.makeText(Main3Activity.this, "download success", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private String[] HttpContent = new String[1];

    private List AudioResource = new ArrayList() {
        @Override
        public Object[] toArray() {
            String[] array = new String[this.size()];
            for (int i = 0; i < this.size(); i++) {
                array[i] = (String) this.get(i);
            }
            return array;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_download_baidu) {
            HttpTools.downloadPageAsync("https://www.uuu228.com/htm/mp3av57/2323.htm", HttpContent);
            Toast.makeText(this, "get html finish", Toast.LENGTH_SHORT).show();
        }
        if (v.getId() == R.id.btn_any_baidu) {
            DocumentTools.getALinks(HttpContent[0]);
        }
        if (v.getId() == R.id.btn_any_resource) {
            DocumentTools.getGetAudio(HttpContent[0], AudioResource);
            Toast.makeText(this, "get resource finish", Toast.LENGTH_SHORT).show();
        }
        if (v.getId() == R.id.btn_download_resource) {
            FileTools.saveFileAsync(AudioResource, mHandler);
        }
    }
}
