package com.example.mycontainer.components;

import androidx.annotation.NonNull;

import com.example.mycontainer.service.DefaultFactory;
import com.example.mycontainer.service.IFactory;


/**
 * 用于配置组件
 *
 * Created by jzj on 2018/4/28.
 */
public class RouterComponents {

    @NonNull
    private static IFactory sDefaultFactory = DefaultFactory.INSTANCE;


    @NonNull
    public static IFactory getDefaultFactory() {
        return sDefaultFactory;
    }

}
