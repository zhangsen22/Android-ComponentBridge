package com.example.mytest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.baseprotocol.IBaseProtocol;
import com.example.mycontainer.bridge.BridgeInterface;
import com.example.mycontainer.bridge.store.BridgeCacheManager;
import com.example.mycontainer.bridge.store.Result;
import com.example.mymodule1.Module1Activity;
import com.example.mymodule2.Module2Activity;
import com.example.mymodule3.Module3Activity;

import java.util.concurrent.ConcurrentHashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new BridgeInterface().init(getApplicationContext(),false);

        ConcurrentHashMap<Class<? extends IBaseProtocol>, Result> classResultConcurrentHashMap = BridgeCacheManager.getInstance().getmInitializedComponents();
        Log.e("tag",classResultConcurrentHashMap.toString());
    }

    public void Module1(View view){
        startActivity(new Intent(this, Module1Activity.class));
    }

    public void Module2(View view){
        startActivity(new Intent(this, Module2Activity.class));
    }

    public void Module3(View view){
        startActivity(new Intent(this, Module3Activity.class));
    }
}