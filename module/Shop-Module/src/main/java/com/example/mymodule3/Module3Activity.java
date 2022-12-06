package com.example.mymodule3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mycontainer.common.MyModule1Modle;

public class Module3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module3);
    }

    public void Module1(View view){
//        MyModule1Modle myModule1Modle = (MyModule1Modle) BridgeProviders.Companion.getInstance().getBridge(Module1Protocol.class).getDataFromModule1();
//        Toast.makeText(this,myModule1Modle.data,Toast.LENGTH_LONG).show();
    }

    public void Module2(View view){
//        BridgeProviders.Companion.getInstance().getBridge(Module1Protocol.class).setMessageToModule1(this);

    }
}