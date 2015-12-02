package com.sarahmizzi.wemote.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.sarahmizzi.wemote.MainActivity;
import com.sarahmizzi.wemote.utilities.NsdHelper;

public class ConnectionService extends Service {
    NsdHelper mNsdHelper;

    public ConnectionService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mNsdHelper = new NsdHelper(this);
        mNsdHelper.initializeDiscoveryListener();
        mNsdHelper.initializeResolveListener();
        mNsdHelper.discoverServices();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNsdHelper.stopDiscovery();
    }
}
