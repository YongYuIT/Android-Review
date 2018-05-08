package com.thinking.keep_alive;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import static com.thinking.keep_alive.MyApp.ProcessName;

public class MainActivity extends AppCompatActivity {

    @TargetApi(23)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                tryDraw();
                return;
            }
            if (Settings.canDrawOverlays(getApplicationContext())) {
                tryDraw();
                return;
            }
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 100);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100) {
            tryDraw();
        }
    }

    @TargetApi(23)
    private void tryDraw() {
        if (Settings.canDrawOverlays(this)) {
            Log.i("yuyong_test", ProcessName + "-->" + this.getClass().getName() + "-->onCreate");
            MyIntentService.startActionBaz(this, "params_1", "params_2");
        } else {
            Log.i("yuyong_test", ProcessName + "-->" + "permission denied ");
        }
    }
}
