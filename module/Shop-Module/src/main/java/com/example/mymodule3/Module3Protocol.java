package com.example.mymodule3;

import android.content.Context;

import com.example.baseprotocol.IBaseProtocol;
import com.example.mycontainer.common.MyModule3Modle;

public interface Module3Protocol extends IBaseProtocol {

    MyModule3Modle getDataFromModule3();
    void setMessageToModule3(Context context);
}
