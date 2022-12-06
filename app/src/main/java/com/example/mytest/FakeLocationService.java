package com.example.mytest;

import android.content.Context;
import android.os.Handler;

import com.example.mycontainer.Router;
import com.example.zhujie.annotation.RouterProvider;
import com.example.zhujie.annotation.RouterService;


/**
 * Created by jzj on 2018/3/26.
 */
@RouterService(interfaces = ILocationService.class, key ="SINGLETON", singleton = true)
public class FakeLocationService implements ILocationService {

    private FakeLocationService(Context context) {
        // ...
    }

    @RouterProvider
    public static FakeLocationService getInstance() {
        return new FakeLocationService(Router.getService(Context.class, "/application"));
    }

    private final Handler mHandler = new Handler();

    @Override
    public boolean hasLocation() {
        return false;
    }

    @Override
    public void startLocation(final LocationListener listener) {
        if (listener != null) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    listener.onSuccess();
                }
            }, 800);
        }
    }
}
