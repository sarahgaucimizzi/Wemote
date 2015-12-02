package com.sarahmizzi.wemote.utilities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.sarahmizzi.wemote.ConnectActivity;
import com.sarahmizzi.wemote.MainActivity;
import com.sarahmizzi.wemote.R;
import com.sarahmizzi.wemote.fragments.RemoteFragment;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import android.app.Activity;

/**
 * Created by Sarah on 01-Dec-15.
 */
public class ConnectionUtils extends Activity implements Runnable {
    final String TAG = "MobileConnectionUtils";
    int port;
    InetAddress host;
    Socket socket;
    boolean connected;
    RemoteFragment remoteFragment;
    Context context;
    String command;

    public ConnectionUtils(int port, String host, Context context) {
        this.port = port;
        try {
            this.host = InetAddress.getByName(host);
        }
        catch(UnknownHostException e){
            Log.e(TAG, e.getMessage());
        }
        this.context = context;
        connected = false;
        remoteFragment = new RemoteFragment();
    }

    @Override
    public void run() {
        try {
            socket = new Socket(host, port);
            connected = true;
            Log.d(TAG, "Starting Remote Fragment");

            Intent intent = new Intent(context, MainActivity.class);
            intent.putExtra("port", port);
            intent.putExtra("host", host.getHostName());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
            connected = false;
        }
    }

    public void processCommand(String s){
        command = s;
        if(s.equals("EXIT")){
            if(socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage());
                }
                connected = false;
                Log.d(TAG, "C: Closed.");

                Intent intent = new Intent(context, ConnectActivity.class);
                context.startActivity(intent);
            }
        }
        else {
            if (connected) {
                try {
                    Log.d("ClientActivity", "C: Sending command.");
                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    // Issue Commands
                    if (!command.equals("")) {
                        out.println(command);
                        Log.d("ClientActivity", "C: Sent.");
                    }
                } catch (Exception e) {
                    Log.e("ClientActivity", "S: Error" + e.getMessage(), e);
                    RemoteFragment remoteFragment = (RemoteFragment) getFragmentManager().findFragmentByTag("remoteFragment");

                    if (remoteFragment == null) {
                        getFragmentManager().beginTransaction()
                                .replace(R.id.container, new RemoteFragment(), "remoteFragment")
                                .commit();

                        remoteFragment = (RemoteFragment) getFragmentManager().findFragmentByTag("remoteFragment");
                    }

                    if (remoteFragment != null) {
                        Log.e("ClientActivity", "S: Error" + e.getMessage());
                    }
                }
            }
        }
    }
}
