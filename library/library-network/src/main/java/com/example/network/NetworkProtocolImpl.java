package com.example.network;


import android.content.Context;
import android.util.Log;

import com.example.zhujie.ComponentBridge;

@ComponentBridge(protocol = INetworkProtocol.class)
public class NetworkProtocolImpl implements INetworkProtocol {


    @Override
    public void onCreate(Context context, boolean b) {
        Log.e("StartUp", "NetworkProtocolImpl: " + "onCreate, isDebug: "+b);
    }

    @Override
    public void onDestroy() {

    }


    @Override
    public void post() {

    }

    @Override
    public void delete() {

    }
}
