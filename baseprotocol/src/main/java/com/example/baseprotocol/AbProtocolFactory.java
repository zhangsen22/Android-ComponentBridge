package com.example.baseprotocol;

import android.content.Context;

public abstract class AbProtocolFactory<T extends IBaseProtocol> {
    /**
     * 模块初始化
     *
     * @param context
     * @return
     */
    public abstract T createProtocol(Context context,boolean isDebug);

    /**
     * 协议类class
     *
     * @return
     */

    public abstract Class<T> getProtocol();

}

