package com.thinking.http_client_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_test_item_1) {
            Intent intent = new Intent(this, Main2Activity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.btn_test_item_2) {
            Intent intent = new Intent(this, Main3Activity.class);
            startActivity(intent);
        }
    }
}
