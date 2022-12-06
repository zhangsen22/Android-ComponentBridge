package com.example.mymodule1;

import android.content.Context;

import com.example.baseprotocol.IBaseProtocol;
import com.example.mycontainer.common.MyModule1Modle;

public interface Module1Protocol extends IBaseProtocol {

    MyModule1Modle getDataFromModule1();
    void setMessageToModule1(Context context);

}
