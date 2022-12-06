package com.example.baseprotocol;

import android.content.Context;

public interface IBaseProtocol {

    void onCreate(Context context,boolean isDebug);

    void onDestroy();
}
