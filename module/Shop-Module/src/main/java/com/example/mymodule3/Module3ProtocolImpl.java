package com.example.mymodule3;

import android.content.Context;
import android.widget.Toast;

import com.example.mycontainer.common.MyModule3Modle;

public class Module3ProtocolImpl implements Module3Protocol {
    @Override
    public MyModule3Modle getDataFromModule3() {
        return new MyModule3Modle("来自 Module3的数据....");
    }

    @Override
    public void setMessageToModule3(Context context) {
        Toast.makeText(context,"给module3发送消息",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreate(Context context, boolean isDebug) {

    }

    @Override
    public void onDestroy() {

    }
}
