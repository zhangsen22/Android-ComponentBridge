package com.example.mymodule2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mycontainer.common.MyModule3Modle;

public class Module2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module2);
    }

    public void Module1(View view){
//        MyModule3Modle myModule3Modle = (MyModule3Modle) BridgeProviders.Companion.getInstance().getBridge(Module3Protocol.class).getDataFromModule3();
//        Toast.makeText(this,myModule3Modle.data,Toast.LENGTH_LONG).show();
    }

    public void Module2(View view){
//        BridgeProviders.Companion.getInstance().getBridge(Module3Protocol.class).setMessageToModule3(this);

    }
}