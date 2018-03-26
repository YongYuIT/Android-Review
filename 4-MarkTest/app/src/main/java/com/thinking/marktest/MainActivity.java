package com.thinking.marktest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    private Button btn_test = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_test = findViewById(R.id.btn_test);
        try {
            AddClickListenerTestHandler.process(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @AddClickListenerTestMark(source = "btn_test")
    public void marked_func(View view) {
        Log.i("yuyong", "marked_func-->" + view.toString());
    }
}
