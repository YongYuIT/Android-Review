package com.thinking.socket;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPTestActivity extends AppCompatActivity {

    private EditText edt_ip;
    private EditText edt_port;
    private EditText edt_msg;

    int current_port = -1;

    private static ExecutorService pool = Executors.newFixedThreadPool(5);

    private Socket mSocket;
    private ServerSocket mServerSocket;
    private OutputStream mOutputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tcptest);
        edt_ip = findViewById(R.id.edt_ip);
        edt_port = findViewById(R.id.edt_port);
        edt_msg = findViewById(R.id.edt_msg);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_conn) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        mSocket = new Socket();
                        mSocket.setReuseAddress(true);
                        mSocket.connect(new InetSocketAddress(edt_ip.getText().toString().trim(), Integer.parseInt(edt_port.getText().toString().trim())));
                        mOutputStream = mSocket.getOutputStream();
                        current_port = mSocket.getLocalPort();
                        mOutputStream.write(String.format("%s:%d\n", mSocket.getLocalAddress().getHostName(), current_port).getBytes());
                        mOutputStream.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
            });
        }
        if (v.getId() == R.id.btn_send) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    String msg = edt_msg.getText().toString();
                    if (!msg.endsWith("\n")) {
                        msg += "\n";
                    }
                    try {
                        mOutputStream.write(msg.getBytes());
                        mOutputStream.flush();
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
                    try {
                        mOutputStream.close();
                        mSocket.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        mServerSocket = new ServerSocket(current_port);
                        Log.e("yuyong", "try accept:" + current_port);
                        mSocket = mServerSocket.accept();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.e("yuyong", "get new client:" + mSocket.getRemoteSocketAddress());
                }
            });
        }
    }
}
