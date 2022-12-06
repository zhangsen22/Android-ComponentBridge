package com.example.mymodule1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class Module1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module1);
    }

    public void Module1(View view){

//        MyModule2Modle myModule2Modle = (MyModule2Modle) BridgeProviders.Companion.getInstance().getBridge(Module2Protocol.class).getDataFromModule2();
//        Toast.makeText(this,myModule2Modle.data,Toast.LENGTH_LONG).show();
    }

    public void Module2(View view){
//        BridgeProviders.Companion.getInstance().getBridge(Module2Protocol.class).setMessageToModule2(this);

    }

}