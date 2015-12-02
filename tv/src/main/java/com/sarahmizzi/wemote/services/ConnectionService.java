package com.sarahmizzi.wemote.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;
import com.sarahmizzi.wemote.utilities.NsdHelper;

public class ConnectionService extends Service {
    final int PORT = 8080;
    NsdHelper mNsdHelper;

    public ConnectionService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Register Service Started", Toast.LENGTH_LONG).show();
        mNsdHelper = new NsdHelper(this);
        mNsdHelper.initializeRegistrationListener();
        mNsdHelper.registerService(PORT);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNsdHelper.unregisterService();
        Toast.makeText(this, "Register Service Stopped", Toast.LENGTH_LONG).show();
    }
}
