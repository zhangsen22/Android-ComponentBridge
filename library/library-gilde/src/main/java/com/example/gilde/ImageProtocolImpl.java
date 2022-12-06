package com.example.gilde;


import android.content.Context;
import android.util.Log;

import com.example.zhujie.ComponentBridge;

@ComponentBridge(protocol = IImageProtocol.class)
public class ImageProtocolImpl implements IImageProtocol {

    @Override
    public void onCreate(Context context, boolean b) {
        Log.e("StartUp", "ImageProtocolImpl: " + "onCreate, isDebug: "+b);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public String imageTest1() {
        return "imageTest1";
    }

    @Override
    public String imageTest2() {
        return "imageTest2";
    }
}
