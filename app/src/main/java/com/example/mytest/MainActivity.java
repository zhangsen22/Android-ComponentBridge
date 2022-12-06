package com.example.mytest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.baseprotocol.IBaseProtocol;
import com.example.mycontainer.Router;
import com.example.mycontainer.bridge.BridgeInterface;
import com.example.mycontainer.bridge.store.BridgeCacheManager;
import com.example.mycontainer.bridge.store.Result;

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
        Integer result = Router.callMethod("ADD_METHOD", 1, 2);
        Toast.makeText(this,"result: "+result,Toast.LENGTH_LONG).show();
    }

    public void Module2(View view) {
        ILocationService locationService = Router.getService(ILocationService.class, "SINGLETON");
        Toast.makeText(this,"locationService: "+locationService.getClass().getSimpleName(),Toast.LENGTH_LONG).show();
        if (locationService.hasLocation()) {
            // 已有定位信息，不做拦截，继续跳转
//            callback.onNext();
            return;
        }
        locationService.startLocation(new ILocationService.LocationListener() {
            @Override
            public void onSuccess() {
                // 定位成功，继续跳转
                Toast.makeText(getApplicationContext(),"定位成功",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure() {
                // 定位失败，不跳转
                Toast.makeText(getApplicationContext(),"定位失败",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void Module3(View view){
        ILocationService locationService = Router.getService(ILocationService.class, "SINGLETON2");
        Toast.makeText(this,"locationService: "+locationService.getClass().getSimpleName(),Toast.LENGTH_LONG).show();
    }
}