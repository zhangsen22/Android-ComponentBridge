package com.example.mymodule2;

import android.content.Context;

import com.example.baseprotocol.IBaseProtocol;
import com.example.mycontainer.common.MyModule2Modle;


public interface Module2Protocol extends IBaseProtocol {
    MyModule2Modle getDataFromModule2();
    void setMessageToModule2(Context context);
}
