package com.sarahmizzi.wemote.broadcast_receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.sarahmizzi.wemote.services.ConnectionService;

/**
 * Created by Sarah on 27-Nov-15.
 */
public class BootReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        intent = new Intent("com.sarahmizzi.wemote.services.ConnectionService");
        intent.setClass(context, ConnectionService.class);
        context.startService(intent);
    }
}
