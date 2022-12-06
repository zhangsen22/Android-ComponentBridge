package com.example.mymodule2;

import android.content.Context;
import android.widget.Toast;

import com.example.mycontainer.common.MyModule2Modle;


public class Module2ProtocolImpl implements Module2Protocol {
    @Override
    public MyModule2Modle getDataFromModule2() {
        return new MyModule2Modle("来自 Module2的数据....");
    }

    @Override
    public void setMessageToModule2(Context context) {
        Toast.makeText(context,"给module2发送消息",Toast.LENGTH_LONG).show();
    }


    @Override
    public void onCreate(Context context, boolean isDebug) {

    }

    @Override
    public void onDestroy() {

    }
}
