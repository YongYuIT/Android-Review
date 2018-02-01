package com.thinking.socket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.ice4j.Transport;
import org.ice4j.TransportAddress;
import org.ice4j.ice.Agent;
import org.ice4j.ice.IceMediaStream;
import org.ice4j.ice.harvest.StunCandidateHarvester;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ICETestActivity extends AppCompatActivity {

    private static ExecutorService pool = Executors.newFixedThreadPool(5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icetest);
    }

    public void onClick(View view) {
        if (view.getId() == R.id.btn_create) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    Agent agent = new Agent();
                    String[] stun_servers = new String[]{"jitsi.org", "numb.viagenie.ca", "stun.ekiga.net", "stun.counterpath.com"};
                    for (int i = 0; i < stun_servers.length; i++) {
                        try {
                            TransportAddress transportAddress = new TransportAddress(InetAddress.getByName(stun_servers[i]), 3478, Transport.UDP);
                            Log.i("yuyong_ice", transportAddress.getHostAddress() + "-->" + transportAddress.getPort());
                            StunCandidateHarvester candidateHarvester = new StunCandidateHarvester(transportAddress);
                            agent.addCandidateHarvester(candidateHarvester);
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    IceMediaStream stream = agent.createMediaStream("fuck-audio");
                    int port = 5555;
                    try {
                        agent.createComponent(stream, Transport.UDP, port, port, port + 100);
                        String dis = SdpUtils.createSDPDescription(agent);
                        Log.i("yuyong_ice", String.format("dis is \"%s\"", dis));
                    } catch (Throwable e) {
                        e.printStackTrace();
                        return;
                    }
                    agent.addStateChangeListener(new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
                            Log.i("yuyong_ice", "event-->" + propertyChangeEvent.getPropertyName());
                        }
                    });
                    agent.startConnectivityEstablishment();
                }
            });
        }
    }
}
