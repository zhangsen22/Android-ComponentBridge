package com.example.mytest;


import com.example.mycontainer.method.Func2;
import com.example.zhujie.annotation.RouterService;

/**
 * Created by jzj on 2018/4/16.
 */
@RouterService(interfaces = Func2.class, key = "ADD_METHOD", singleton = true)
public class AddMethod implements Func2<Integer, Integer, Integer> {

    @Override
    public Integer call(Integer a, Integer b) {

        return a + b;
    }
}
