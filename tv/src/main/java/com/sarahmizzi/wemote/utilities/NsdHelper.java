package com.sarahmizzi.wemote.utilities;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.util.Log;

/**
 * Created by Sarah on 30-Nov-15.
 */
public class NsdHelper {
    final String TAG = "TvNsdHelper";
    public static final String SERVICE_TYPE = "_http._tcp.";
    public String mServiceName = "Wemote";

    Context mContext;
    NsdManager mNsdManager;
    NsdManager.RegistrationListener mRegistrationListener;

    public NsdHelper(Context context) {
        mContext = context;
        mNsdManager = (NsdManager) context.getSystemService(Context.NSD_SERVICE);
    }

    public void registerService(int port) {
        // Create the NsdServiceInfo object, and populate it.
        NsdServiceInfo serviceInfo  = new NsdServiceInfo();

        serviceInfo.setServiceName(mServiceName);
        serviceInfo.setServiceType(SERVICE_TYPE);
        serviceInfo.setPort(port);

        mNsdManager.registerService(
                serviceInfo, NsdManager.PROTOCOL_DNS_SD, mRegistrationListener);
    }

    public void initializeRegistrationListener() {
        mRegistrationListener = new NsdManager.RegistrationListener() {

            @Override
            public void onServiceRegistered(NsdServiceInfo NsdServiceInfo) {
                mServiceName = NsdServiceInfo.getServiceName();
                Thread thread = new Thread(new ConnectionUtils(mServiceName, mContext));
                thread.start();
                Log.d(TAG, "Thread Started");
            }

            @Override
            public void onRegistrationFailed(NsdServiceInfo arg0, int arg1) {
            }

            @Override
            public void onServiceUnregistered(NsdServiceInfo arg0) {
            }

            @Override
            public void onUnregistrationFailed(NsdServiceInfo serviceInfo, int errorCode) {
            }

        };
    }

    public void unregisterService(){
        mNsdManager.unregisterService(mRegistrationListener);
    }
}
