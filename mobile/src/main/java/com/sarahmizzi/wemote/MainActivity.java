package com.sarahmizzi.wemote;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.sarahmizzi.wemote.fragments.RemoteFragment;
import com.sarahmizzi.wemote.utilities.ConnectionUtils;

public class MainActivity extends Activity implements RemoteFragment.OnButtonPressedListener{
    Context mContext = this;
    int port;
    String host;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle serviceInfo = getIntent().getExtras();
        port = serviceInfo.getInt("port");
        host = serviceInfo.getString("host");

        getFragmentManager().beginTransaction()
                .replace(R.id.container, new RemoteFragment(), "remoteFragment")
                .commit();
    }

    @Override
    public void buttonPressed(String s) {
        ConnectionUtils connectionUtils = new ConnectionUtils(port, host, mContext);
        connectionUtils.processCommand(s);
    }
}
