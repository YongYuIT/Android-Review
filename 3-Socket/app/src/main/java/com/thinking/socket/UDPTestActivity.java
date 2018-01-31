package com.thinking.socket;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UDPTestActivity extends AppCompatActivity {

    private DatagramSocket mSocket;
    private DatagramSocket mNewSocket;
    private DatagramPacket mPacket;
    private EditText edt_ip;
    private EditText edt_port;
    private EditText edt_msg;
    private TextView txt_ip;

    private int current_port = 8888;

    private static ExecutorService pool = Executors.newFixedThreadPool(5);
    private Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_udptest);
        edt_ip = findViewById(R.id.edt_ip);
        edt_port = findViewById(R.id.edt_port);
        edt_msg = findViewById(R.id.edt_msg);
        txt_ip = findViewById(R.id.txt_ip);
        pool.execute(new Runnable() {
            @Override
            public void run() {
                final String ip = getHostIP();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        txt_ip.setText(ip);
                    }
                });
            }
        });
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_conn) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (mSocket == null)
                            mSocket = new DatagramSocket(current_port);
                        InetAddress target_address = InetAddress.getByName(edt_ip.getText().toString().trim());
                        byte[] buf = String.format("%s:%d\n", getHostIP(), current_port).getBytes();
                        mPacket = new DatagramPacket(buf, buf.length, target_address, Integer.parseInt(edt_port.getText().toString().trim()));
                        mSocket.send(mPacket);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }
        if (v.getId() == R.id.btn_send) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        InetAddress target_address = InetAddress.getByName(edt_ip.getText().toString().trim());
                        byte[] buf = edt_msg.getText().toString().getBytes();
                        mPacket = new DatagramPacket(buf, buf.length, target_address, Integer.parseInt(edt_port.getText().toString().trim()));
                        mSocket.send(mPacket);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        if (v.getId() == R.id.btn_change) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    byte[] received_data = new byte[1024];
                    while (true) {
                        DatagramPacket receivePacket = new DatagramPacket(received_data, received_data.length);
                        try {
                            mSocket.receive(receivePacket);
                            String sentence = new String(receivePacket.getData(), 0, receivePacket.getLength(), "utf-8");
                            Log.e("yuyong_udp", sentence);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            });
        }
        if (v.getId() == R.id.btn_new) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        mNewSocket = new DatagramSocket(4444);
                        byte[] received_data = new byte[1024];
                        while (true) {
                            DatagramPacket receivePacket = new DatagramPacket(received_data, received_data.length);
                            try {
                                mNewSocket.receive(receivePacket);
                                String sentence = new String(receivePacket.getData(), 0, receivePacket.getLength(), "utf-8");
                                Log.e("yuyong_udp_new", sentence);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        }

    }

    public static String getHostIP() {

        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return hostIp;

    }
}
