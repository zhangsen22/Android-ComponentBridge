package com.example.mymodule1;

import android.content.Context;
import android.widget.Toast;

import com.example.mycontainer.common.MyModule1Modle;

public class Module1ProtocolImpl implements Module1Protocol {
    @Override
    public MyModule1Modle getDataFromModule1() {
        return new MyModule1Modle("来自 Module1的数据....");
    }

    @Override
    public void setMessageToModule1(Context context) {
        Toast.makeText(context,"给module1发送消息",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreate(Context context, boolean isDebug) {

    }

    @Override
    public void onDestroy() {

    }
}
