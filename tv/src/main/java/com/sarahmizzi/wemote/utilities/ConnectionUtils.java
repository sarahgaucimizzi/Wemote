package com.sarahmizzi.wemote.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;

import com.sarahmizzi.wemote.MainActivity;
import com.sarahmizzi.wemote.MainFragment;
import com.sarahmizzi.wemote.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Sarah on 01-Dec-15.
 */
public class ConnectionUtils extends Activity implements Runnable {
    final int PORT = 8080;
    Context context;
    ServerSocket serverSocket;
    Socket client;
    String ip;
    MainFragment fragment;

    private Handler handler = new Handler();

    public ConnectionUtils(String ip, Context context) {
        this.ip = ip;
        this.context = context;
        fragment = new MainFragment();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(PORT);
            while (true) {
                // Listen for incoming clients
                client = serverSocket.accept();

                if (!serverSocket.isClosed()) {
                    listenForCommand();
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public void listenForCommand() {
        try {
            // Listen for remote commands and execute them
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            final String line = in.readLine();
            if (line != null) {
                Log.d("ServerActivity", line);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        switch (line) {
                            case "UP":
                                dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_UP));
                                dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DPAD_UP));
                                break;

                            case "DOWN":
                                dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN));
                                dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DPAD_DOWN));
                                break;

                            case "LEFT":
                                dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_LEFT));
                                dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DPAD_LEFT));
                                break;

                            case "RIGHT":
                                dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_RIGHT));
                                dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_DPAD_RIGHT));
                                break;

                            case "OK":
                                dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));
                                dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ENTER));
                                break;

                            case "EXIT":
                                if (!serverSocket.isClosed()) {
                                    try {
                                        serverSocket.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }

                                Intent intent = new Intent(context, MainActivity.class);
                                startService(intent);
                                finish();
                                break;
                        }
                    }
                });
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }

        if (!serverSocket.isClosed()) {
            listenForCommand();
        }
    }
}
