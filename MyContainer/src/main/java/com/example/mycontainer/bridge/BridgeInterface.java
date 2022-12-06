package com.example.mycontainer.bridge;

import android.content.Context;
import androidx.annotation.NonNull;
import com.example.baseprotocol.AbProtocolFactory;
import com.example.baseprotocol.IBaseProtocol;
import com.example.mycontainer.bridge.store.Result;
import com.example.mycontainer.bridge.store.BridgeCacheManager;
import java.util.concurrent.ConcurrentHashMap;

public class BridgeInterface {
    private static final String TAG = BridgeInterface.class.getSimpleName();
    private static Context mContext;
    private static boolean isDebug;
    private static boolean registerByPlugin;

    public void init(Context context, boolean debug) {
        mContext = context;
        isDebug = debug;
        //加载插件插入字节码
        insertCodeByMSA();
        if (registerByPlugin) {

        }
    }

    /**
     * ASM忘此方法插入初始化的代码
     */
    private void insertCodeByMSA() {
        registerByPlugin = false;
//         registerModule(Annotation$$JDApmModule.class,new Annotation$$ApmModule());
//         registerModule(Annotation$$JDApmModule.class,new JDAnnotation$$ApmModule());
    }

    private static <T extends IBaseProtocol> void registerModule(Class<? extends AbProtocolFactory<T>> abProtocolFactoryClass, AbProtocolFactory<T> abProtocolFactory) {
        markRegisteredByPlugin();
        BridgeCacheManager.getInstance().saveInitializedComponent(abProtocolFactory.getProtocol(), new Result(abProtocolFactory.createProtocol(mContext,isDebug)));
    }


    /**
     * mark already registered by jd_annotation_register plugin
     */
    private static void markRegisteredByPlugin() {
        if (!registerByPlugin) {
            registerByPlugin = true;
        }
    }

    public static <T extends IBaseProtocol> T getBridge(@NonNull Class<T> clz) {
        ConcurrentHashMap<Class<? extends IBaseProtocol>, Result> classResultConcurrentHashMap = BridgeCacheManager.getInstance().getmInitializedComponents();
        if (classResultConcurrentHashMap == null) {
            throw new IllegalStateException("regist moudle fail by jd_startup_register plugin.");
        }

        if (!classResultConcurrentHashMap.containsKey(clz)) {
            throw new RuntimeException("Not found " + clz.getSimpleName() + "   in this map!");
        }

        IBaseProtocol obj = (IBaseProtocol) classResultConcurrentHashMap.get(clz);
        if (obj == null) {
            throw new RuntimeException("found " + clz.getSimpleName() + " in this map is null !");
        }
        return clz.cast(obj);
    }
}
