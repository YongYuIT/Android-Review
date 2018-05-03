package com.thinking.a6_cmake;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.thinking.a5_encryption.EncrypTools;
import com.thinking.a5_encryption.TestTools;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    static String txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());

        String id = EncrypTools.getID("fuck you");
        id = EncrypTools.getID("All the presentations will be available on YouTube in about a month. For now, you can take a sneak peek by browsing the slide decks: from Rails 6 to Active Storage, from Migrations to Testing, from Upgrades to API, from Router to C");

        String des = EncrypTools.doDeCryp("testtesttesttesttesttesttesttesttest", "fuck you");
        des = EncrypTools.doDeCryp("testtesttesttesttesttesttesttesttest", "All the presentations will be available on YouTube in about a month. For now, you can take a sneak peek by browsing the slide decks: from Rails 6 to Active Storage, from Migrations to Testing, from Upgrades to API, from Router to C");

        TestTools.do_test();
        txt = "All the presentations will be available on YouTube in about a month. For now, you can take a sneak peek by browsing the slide decks: from Rails 6 to Active Storage, from Migrations to Testing, from Upgrades to API, from Router to Contributing, from GraphQL to Performance, from Collaboration to Communication, from Kafka to PostgreSQL, from Warden to Authorization, from Economy to Art, from Bugs to Security, from Containers to Crypto, from Interviews to History, from Teams to Trust, from Shopify to GitHub, from Engines to Crowdsourcing, from Mortality to Life, there was really something for everyone!";
        txt += txt;
        txt += txt;
        txt += txt;
        txt += txt;
        txt += txt;
        txt += txt;
        txt += txt;
        txt += txt;
        txt += txt;
        txt += txt;
        txt += txt;
        txt = "這裏是中文測試單元";
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (1 == 0) {
                    for (int i = 0; i < 10000; i++) {
                        Log.i("yuyong", i + "------------------------" + i);
                        String key = "testtesttesttesttesttesttesttesttest";
                        String out = EncrypTools.doEnCryp(key, txt);
                        Log.i("yuyong", "--eny-->" + out);
                        String de_out = EncrypTools.doDeCryp(key, out);
                        Log.i("yuyong", "--dey-->" + de_out);
                    }
                } else {
                    for (int i = 0; i < 10000; i++) {
                        Log.i("yuyong", i + "------------------------" + i);
                        String key = "testtesttesttesttesttesttesttesttest";
                        String out = EncrypTools.doEnCryp(key, "testtesttesttest", txt);
                        Log.i("yuyong", "--eny-->" + out);
                        String id = EncrypTools.getID(out);
                        Log.i("yuyong", "--id-->" + id);
                        String de_out = EncrypTools.doDeCryp(key, out);
                        Log.i("yuyong", "--dey-->" + de_out);
                    }
                }
            }
        }).start();
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
