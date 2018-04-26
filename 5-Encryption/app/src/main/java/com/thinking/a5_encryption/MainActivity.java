package com.thinking.a5_encryption;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    static String txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    try {
                        Log.i("yuyong", i + "------------------------" + i);
                        String key = "testtesttesttesttesttesttesttesttest";
                        String out = EncrypTools.doEnCryp(key, "testtesttesttest", txt);
                        Log.i("yuyong", "--eny-->" + out);
                        String id = EncrypTools.getID(out);
                        Log.i("yuyong", "--id-->" + id);
                        String de_out = EncrypTools.doDeCryp(key, out);
                        Log.i("yuyong", "--dey-->" + de_out);
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }

                }
            }
        }).start();

    }
}
