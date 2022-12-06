package com.example.mycontainer.bridge.store;


import androidx.annotation.NonNull;

import com.example.baseprotocol.IBaseProtocol;

import java.util.concurrent.ConcurrentHashMap;

public class BridgeCacheManager {

    //用于缓存每一个任务的执行结果    Result用来给ConcurrentHashMap防null
    @NonNull
    private ConcurrentHashMap<Class<? extends IBaseProtocol>, Result> mInitializedComponents =
            new ConcurrentHashMap();


    private BridgeCacheManager() {
    }

    private static class SingletonFactory {
        private static BridgeCacheManager instance = new BridgeCacheManager();
    }

    public static BridgeCacheManager getInstance() {
        return SingletonFactory.instance;
    }

    /**
     * save result of initialized component.
     */
    public void saveInitializedComponent(Class<? extends IBaseProtocol> zClass, Result result) {
        mInitializedComponents.put(zClass, result);
    }

    /**
     * check initialized.
     */
    public boolean hadInitialized(Class<? extends IBaseProtocol> zClass) {
        return mInitializedComponents.containsKey(zClass);
    }

    public <T> Result<T> obtainInitializedResult(Class<? extends IBaseProtocol> zClass) {
        return mInitializedComponents.get(zClass);
    }

    @NonNull
    public ConcurrentHashMap<Class<? extends IBaseProtocol>, Result> getmInitializedComponents() {
        return mInitializedComponents;
    }

    public void remove(Class<? extends IBaseProtocol> zClass) {
        mInitializedComponents.remove(zClass);
    }

    public void clear() {
        mInitializedComponents.clear();
    }
}
