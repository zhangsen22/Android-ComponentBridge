package com.example.mytest;

/**
 * Created by jzj on 2018/4/19.
 */

public interface ILocationService {

    boolean hasLocation();

    void startLocation(LocationListener listener);

    interface LocationListener {

        void onSuccess();

        void onFailure();
    }
}
